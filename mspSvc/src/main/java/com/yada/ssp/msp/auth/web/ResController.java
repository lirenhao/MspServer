package com.yada.ssp.msp.auth.web;

import com.yada.ssp.msp.auth.model.Res;
import com.yada.ssp.msp.auth.model.ResOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/res_list")
public class ResController {

    @Value("#{'${res.exclude.paths:/res_list,/error,/app}'.split(',')}")
    private List<String> exPaths;

    private final ApplicationContext applicationContext;

    @Autowired
    public ResController(WebApplicationContext webApplicationContext) {
        this.applicationContext = webApplicationContext;
    }

    @GetMapping
    public List<Res> index() {
        List<Res> resList = new ArrayList<>();
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Set<RequestMappingInfo> infos = bean.getHandlerMethods().keySet();
        infos.forEach(info -> info.getPatternsCondition().getPatterns().stream()
                .filter(uri -> !exPaths.contains(uri))
                .forEach(uri -> resList.add(
                        new Res(uri, info.getMethodsCondition().getMethods().stream()
                                .map(method -> ResOps.get(method.name()))
                                .collect(Collectors.toList()).toArray(new ResOps[]{})
                        ))
                )
        );
        return resList.stream().map(Res::getUri).distinct()
                .map(uri -> new Res(uri, resList.stream()
                        .filter(tmp -> tmp.getUri().equals(uri))
                        .map(Res::getOps)
                        .filter(Objects::nonNull)
                        .map(ops -> new ArrayList<>(Arrays.asList(ops)))
                        .reduce(new ArrayList<>(), (a, b) -> {
                            b.addAll(a);
                            return b;
                        })
                        .toArray(new ResOps[]{})
                ))
                .collect(Collectors.toList());
    }
}
