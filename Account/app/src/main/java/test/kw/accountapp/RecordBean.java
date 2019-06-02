package test.kw.accountapp;

import android.util.Log;

import java.util.UUID;

import test.kw.accountapp.util.DateUtil;

/**
 * 账目类
 */
public class RecordBean {
    //消费类别
    public enum RecordType {
        //输入  支出
        RECODE_TYPR_EXPENSE, RECODE_TYPR_INCOME
    }

    //消费金额
    private double amount;
    //消费类别
    private RecordType recordType;
    //分类
    private String category;
    //备注
    private String remark;
    //日期
    private String date;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getRecordType() {
        if(this.recordType == RecordType.RECODE_TYPR_EXPENSE){
            return 1;
        }else{
            return 2;
        }
    }

    public void setRecordType(int recordType) {
        if(recordType==1){
            this.recordType= RecordType.RECODE_TYPR_EXPENSE;
        }else {
            this.recordType = RecordType.RECODE_TYPR_INCOME;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    //时间   存储为一个时间戳
    private long timeStamp;
    //主键
    private String uuid;

    //构造方法
    public RecordBean() {
        //主键UUID
        this.uuid = UUID.randomUUID().toString();
        //Log.d("uuid", uuid + "====================================");
        //时间戳
        this.timeStamp = System.currentTimeMillis();
        //Log.d("timestamp", DateUtil.getFormattedTime(timeStamp) + "====================================");
        date = DateUtil.getFormattedDate();
    }
}