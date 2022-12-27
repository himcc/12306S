package com.example.cn12306s.controller;

import com.example.cn12306s.config.DBUserDetailsService;
import com.example.cn12306s.dto.RetData;
import com.example.cn12306s.entity.StationEntity;
import com.example.cn12306s.entity.TrainEntity;
import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.StationService;
import com.example.cn12306s.service.TrainService;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private StationService stationService;

    @Autowired
    private TrainService trainService;

    @GetMapping("/admin/getalluser")
    public RetData getAllUser(){
        List<UserEntity> allUser = userService.getAllUser();
        RetData ret = new RetData();
        ret.setCode(10000);
        ret.setMsg("ok");
        ret.setData(allUser);
        return ret;
    }

    @GetMapping("/admin/roleswitch")
    public RetData roleSwitch(@RequestParam Integer uid, @RequestParam Integer newRole){
        RetData ret = new RetData();

        try {
            UserEntity user = new UserEntity();
            user.setId(uid);
            user.setSysRole(newRole);

            userService.changeSysRole(user);
            ret.setCode(10000);
            ret.setMsg("成功！");
            return ret;
        } catch (Exception e) {
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

    @GetMapping("/admin/getallstation")
    public RetData getAllStation(){
        List<StationEntity> allStation = stationService.getAllStation();
        RetData ret = new RetData();
        ret.setCode(10000);
        ret.setMsg("ok");
        ret.setData(allStation);
        return ret;
    }

    @PostMapping("/admin/addstation")
    public RetData addStation(@RequestParam String stationName,@RequestParam String cityName){
        RetData ret = new RetData();

        try{
            StationEntity station = new StationEntity();
            station.setStationName(stationName);
            station.setCityName(cityName);
            stationService.addStation(station);
            ret.setCode(10000);
            ret.setMsg("成功！");
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


    @GetMapping("/admin/getalltrain")
    public RetData getAllTrain(){
        List<TrainEntity> allTrain = trainService.getAllTrain();
        RetData ret = new RetData();
        ret.setCode(10000);
        ret.setMsg("ok");
        ret.setData(allTrain);
        return ret;
    }

    @PostMapping("/admin/addtrain")
    public RetData addTrain(@RequestParam String trainName,@RequestParam String stationJson,@RequestParam String carJson){
        RetData ret = new RetData();

        try{
            TrainEntity train = new TrainEntity();
            train.setTrainName(trainName);
            train.setStationJson(stationJson);
            train.setCarJson(carJson);
            trainService.addTrain(train);
            ret.setCode(10000);
            ret.setMsg("成功！");
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

    @GetMapping("/admin/deltrain")
    public RetData delTrain(@RequestParam Integer id){
        RetData ret = new RetData();
        try{
            trainService.deleteTrain(id);
            ret.setCode(10000);
            ret.setMsg("ok");
            return ret;
        }catch (Exception e){
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }

    }
}
