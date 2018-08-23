package com.incarcloud.saic.modes.AS24;

import com.incarcloud.saic.GB32960.GBx06Peak;
import com.incarcloud.saic.modes.MongoX;
import com.incarcloud.saic.modes.mongo.IMongoX06Peak;
import org.bson.Document;
import java.time.ZonedDateTime;
/**
 * @author wanghan
 * @date 2018/8/21 15:02
 */
public class AS24x06Peak extends MongoX implements IMongoX06Peak {

    public GBx06Peak makeGBx06Peak(Document bsonDoc){
        String vin = super.getVin(bsonDoc);
        ZonedDateTime tmGMT8 = super.getZonedDateTimeGMT8(bsonDoc);

        short vehBMSCellMaxVolIndx = (short)parseIntWithDef(bsonDoc, "vehBMSCellMaxVolIndx");
        int vehBMSCellMaxVolV = parseIntWithDef(bsonDoc, "vehBMSCellMaxVolV");
        float vehBMSCellMaxVol = parseFloatWithDef(bsonDoc, "vehBMSCellMaxVol");
        short vehBMSCellMinVolIndx =  (short)parseIntWithDef(bsonDoc, "vehBMSCellMinVolIndx");
        int vehBMSCellMinVolV = parseIntWithDef(bsonDoc, "vehBMSCellMinVolV");
        float vehBMSCellMinVol = parseFloatWithDef(bsonDoc, "vehBMSCellMinVol");
        short vehBMSCellMaxTemIndx =  (short)parseIntWithDef(bsonDoc, "vehBMSCellMaxTemIndx");
        GBx06Peak data = new GBx06Peak(vin, tmGMT8);
        //最高电压电池子系统号=0x01
        data.setHighBatteryId((short) 0x01);
        //最高电压电池单体代号 = vehBMSCellMaxVolIndx
        data.setHighBatteryCode(vehBMSCellMaxVolIndx);
        //电 池 单 体 电 压 最 高 值
        data.setHighVoltage(singleBatteryVoltage(vehBMSCellMaxVolV, vehBMSCellMaxVol));
        //最低电压电池子系统号
        data.setLowBatteryId((short) 0x1);
        //最低电压电池单体代号
        data.setLowBatteryCode(vehBMSCellMinVolIndx);
        //电池单体电压最低值
        data.setLowVoltage(minimumVoltageOfsingle(vehBMSCellMinVolV, vehBMSCellMinVol));
        //最高温度子系统号
        data.setHighTemperatureId((short) 0x1);
        //最高温度探针序号
        data.setHighProbeCode(vehBMSCellMaxTemIndx);
        //最高温度值
        short vehBMSCellMaxTemV = (short)parseIntWithDef(bsonDoc, "vehBMSCellMaxTemV");
        short vehBMSCellMaxTem = (short)parseIntWithDef(bsonDoc, "vehBMSCellMaxTem");
        data.setHighTemperature(highestTemperatureSubsystem(vehBMSCellMaxTemV, vehBMSCellMaxTem));
        //最低温度子系统号
        data.setLowTemperatureId((short) 0x1);
        //最低温度探针序号
        short vehBMSCellMinTemIndx =  (short)parseIntWithDef(bsonDoc, "vehBMSCellMinTemIndx");
        data.setLowProbeCode(vehBMSCellMinTemIndx);
        //最低温度值
        int vehBMSCellMinTemV = parseIntWithDef(bsonDoc, "vehBMSCellMinTemV");
        short vehBMSCellMinTem = (short)Math.round(parseFloatWithDef(bsonDoc, "vehBMSCellMinTem"));
        data.setLowTemperature(minimumStorageSubsystem(vehBMSCellMinTemV, vehBMSCellMinTem));
        return data;
    }

    private static float singleBatteryVoltage(int vehBMSCellMaxVolV, float vehBMSCellMaxVol){
        /*
        IF vehBMSCellMaxVolV=1
        THEN 电 池 单 体 电 压 最 高 值
        =0xFF,0xFF
        ELSE 电 池 单 体 电 压 最 高 值 =
        vehBMSCellMaxVol
         */
        float singleBattery;
        if(vehBMSCellMaxVolV == 1) singleBattery = 0xFFFF;
        else singleBattery = vehBMSCellMaxVol;
        return singleBattery;
    }

    private static float minimumVoltageOfsingle(int vehBMSCellMinVolV, float vehBMSCellMinVol){
        /*
        IF vehBMSCellMinVolV=1
        THEN 电 池 单 体 电 压 最 低 值
        =0xFF,0xFF
        ELSE 电 池 单 体 电 压 最 低 值 =
        vehBMSCellMinVol
         */
        float minimumVoltage;
        if(vehBMSCellMinVolV == 1) minimumVoltage = 0xFFFF;
        else minimumVoltage = vehBMSCellMinVol ;
        return minimumVoltage;
    }

    private static short highestTemperatureSubsystem(int vehBMSCellMaxTemV, short vehBMSCellMaxTem){
        /*
        IF vehBMSCellMaxTemV=1
        THEN 最高温度值=0xFF
        ELSE 最 高 温 度 值 =
        vehBMSCellMaxTem
         */
        short highestSubsystem;
        if(vehBMSCellMaxTemV == 1) highestSubsystem = (byte) 0xFF;
        else highestSubsystem =  vehBMSCellMaxTem ;
        return highestSubsystem;
    }

    private static short minimumStorageSubsystem(int vehBMSCellMinTemV,short vehBMSCellMinTem){
        /*
        IF vehBMSCellMinTemV=1
        THEN 最低温度值=0xFF
        ELSE 最 低 温 度 值 =
        vehBMSCellMinTem
        */
        short minimumStorage;
        if(vehBMSCellMinTemV == 1) minimumStorage = (short) 0xFF;
        else minimumStorage = (short) vehBMSCellMinTem ;
        return minimumStorage;
    }
}
