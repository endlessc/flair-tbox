package com.incarcloud.saic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="saic2017")
public class SAIC2017Config {
    private static final DateTimeFormatter s_fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private LocalDate beginDate;
    public LocalDate getBeginDate(){ return beginDate; }
    public void setBeginDate(String val){
        beginDate = LocalDate.parse(val, s_fmt);
    }

    private LocalDate endDate;
    public LocalDate getEndDate(){ return endDate; }
    public void setEndDate(String val){
        endDate = LocalDate.parse(val, s_fmt);
    }

    private String out;
    public String getOut(){ return out; }
    public void setOut(String val){ out = val; }

    private final List<String> dataSources = new ArrayList<>();
    public List<String> getDataSources (){ return dataSources; }

    private final MongoConfig mongo = new MongoConfig();
    public MongoConfig getMongo(){ return mongo; }

    private final OracleConfig oracle = new OracleConfig();
    public OracleConfig getOracle(){ return oracle; }

    private final JsonConfig json = new JsonConfig();
    public JsonConfig getJson(){ return json; }

    private int maxPower;
    public int getMaxPower(){ return maxPower; }
    public void setMaxPower(int val){ maxPower = val; }

    private final List<String> modes = new ArrayList<>();
    public List<String> getModes(){ return modes; }

    private final List<String> gb32960 = new ArrayList<>();
    public List<String> getGB32960(){ return gb32960; }

    private String vinMatch;
    public String getVinMatch(){ return vinMatch; }
    public void setVinMatch(String val){ vinMatch = val; }
}
