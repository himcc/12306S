package com.example.cn12306s.controller;

import com.example.cn12306s.config.DBUserDetailsService;
import com.example.cn12306s.dto.QueryExeTrain;
import com.example.cn12306s.dto.QueryInfo;
import com.example.cn12306s.dto.RetData;
import com.example.cn12306s.dto.SeatOccupancyInfo;
import com.example.cn12306s.entity.OrderEntity;
import com.example.cn12306s.entity.StationEntity;
import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.OrderService;
import com.example.cn12306s.service.SeatService;
import com.example.cn12306s.service.StationService;
import com.example.cn12306s.service.UserService;
import com.example.cn12306s.utils.SeatType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private StationService stationService;

    @Autowired
    private OrderService orderService;

    public static long getUid() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Object principal = authentication.getPrincipal();
            DBUserDetailsService.DBUser user = (DBUserDetailsService.DBUser)principal;
            return user.getId();
        }else{
            throw new Exception("用户为登录，请登录后再修改密码");
        }
    }

    @GetMapping("/")
    public ModelAndView index(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView mv=new ModelAndView("index.html");
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Object principal = authentication.getPrincipal();
            DBUserDetailsService.DBUser user = (DBUserDetailsService.DBUser)principal;
            mv.addObject("uid",user.getId());
        }
        return mv;
    }

    @PostMapping("/chgpwd")
    public RetData changePasswd(@RequestParam String password){
        RetData ret = new RetData();
        try {
            UserEntity user = new UserEntity();
            long uid = getUid();
            user.setPasswd(new BCryptPasswordEncoder().encode(password));
            user.setId(uid);
            userService.changePasswd(user);
            ret.setCode(10000);
            ret.setMsg("成功！");
            return ret;
        } catch (Exception e) {
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

    @PostMapping("/reg")
    public RetData reg(@RequestParam String username,@RequestParam String password){
        RetData ret = new RetData();

        try{
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPasswd(new BCryptPasswordEncoder().encode(password));
            userService.addUser(user);
            ret.setCode(10000);
            ret.setMsg("注册成功！");
            return ret;
        }catch (DuplicateKeyException e){
            ret.setCode(10001);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }catch (Exception e){
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

    @GetMapping("/querySeat")
    public RetData querySeat(@RequestParam String startDate,@RequestParam String startPos,@RequestParam String endPos){
        RetData ret = new RetData();

        try{
            QueryInfo q = new QueryInfo();
            long nowTs = System.currentTimeMillis();

            DateTime dt = new DateTime(startDate);
            long startTs = dt.getMillis();
            long endTs = dt.plusDays(1).getMillis();

            if(nowTs>startTs){
                startTs=nowTs;
            }



            q.setNowTs(nowTs);
            q.setStartTs(startTs);
            q.setEndTs(endTs);
            if(startPos.endsWith("站")){
                q.setStartStation(startPos);
            }else{
                q.setStartCity(startPos);
            }
            if(endPos.endsWith("站")){
                q.setEndStation(endPos);
            }else{
                q.setEndCity(endPos);
            }

            List<QueryExeTrain> queryExeTrains = seatService.querySeat(q);

            ret.setCode(10000);
            ret.setMsg("成功！");
            ret.setData(queryExeTrains);
            return ret;
        }catch (DuplicateKeyException e){
            ret.setCode(10001);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

    @GetMapping("/getallstation")
    public RetData getAllStation(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RetData ret = new RetData();
        if(authentication instanceof AnonymousAuthenticationToken){
            ret.setCode(302);
            ret.setMsg("ok");
            return ret;
        }

        List<StationEntity> allStation = stationService.getAllStation();
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        for(StationEntity e : allStation){
            if(map.containsKey(e.getCityName())){
                map.get(e.getCityName()).add(e.getStationName());
            }else {
                Set<String> s = new HashSet<>();
                s.add(e.getStationName());
                map.put(e.getCityName(),s);
            }
        }
        List<String> retd = new ArrayList<String>();
        for(Map.Entry<String, Set<String>> e : map.entrySet()){
            retd.add(e.getKey());
            for(String station : e.getValue()){
                retd.add(station);
            }
        }

        ret.setCode(10000);
        ret.setMsg("ok");
        ret.setData(retd);
        return ret;
    }


    @PostMapping("/createorder")
    public RetData createOrder(@RequestParam Integer exeTrainId
            ,@RequestParam Integer leaveStationNo
            ,@RequestParam Integer arriveStationNo
            ,@RequestParam String seatType){

        RetData ret = new RetData();

        try{
            SeatOccupancyInfo s = new SeatOccupancyInfo();
            s.setExeTrainId(exeTrainId)
                .setLeaveStationNo(leaveStationNo)
                .setArriveStationNo(arriveStationNo)
                .setSeatType(SeatType.getId(seatType));
            long uid = getUid();
            OrderEntity orderEntity = seatService.orderSeat(s, uid);
            ret.setCode(10000);
            ret.setMsg("下单成功！");
            ret.setData(orderEntity);
            return ret;
        }catch (Exception e){
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

    @GetMapping("/paysuccess")
    public RetData paySuccess(@RequestParam Long orderId){
        RetData ret = new RetData();

        try{
            orderService.paySuccess(orderId);
            ret.setCode(10000);
            ret.setMsg("支付成功！");
            return ret;
        }catch (Exception e){
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

    @GetMapping("/getmyorders")
    public RetData getMyOrders(){
        RetData ret = new RetData();

        try{
            long uid = getUid();
            List<OrderEntity> orders = orderService.getOrderSByUid(uid);
            ret.setCode(10000);
            ret.setMsg("成功！");
            ret.setData(orders);
            return ret;
        }catch (Exception e){
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

    @GetMapping("/refundticket")
    public RetData refundTicket(@RequestParam Long orderId){
        RetData ret = new RetData();

        try{
            orderService.refundTicket(orderId);
            ret.setCode(10000);
            ret.setMsg("退票成功！");
            return ret;
        }catch (Exception e){
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

}