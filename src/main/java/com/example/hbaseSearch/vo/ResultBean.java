//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.hbaseSearch.vo;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;
    private Long date;
    private T data;

    public ResultBean(T data) {
        this.code = "000000";
        this.msg = "";
        this.date = System.currentTimeMillis();
        this.data = data;
    }

    public static ResultBean success() {
        return new ResultBean("000000", "成功", System.currentTimeMillis(), (Object)null);
    }

    public static ResultBean error(String errorCode) {
        return new ResultBean(errorCode, "", System.currentTimeMillis(), (Object)null);
    }

    public static ResultBean error(String errorCode, String errorMsg) {
        return new ResultBean(errorCode, errorMsg, System.currentTimeMillis(), (Object)null);
    }

    public static ResultBean success(Object data) {
        return new ResultBean("000000", "成功", System.currentTimeMillis(), data);
    }

    public static ResultBean uncaughtedError() {
        return new ResultBean("999999", "未知异常", System.currentTimeMillis(), (Object)null);
    }

    public Boolean hasError() {
        return !"000000".equals(this.getCode());
    }

    public ResultBean(final String code, final String msg, final Long date, final T data) {
        this.code = code;
        this.msg = msg;
        this.date = date;
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Long getDate() {
        return this.date;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setDate(final Long date) {
        this.date = date;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultBean)) {
            return false;
        } else {
            ResultBean<?> other = (ResultBean)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$date = this.getDate();
                    Object other$date = other.getDate();
                    if (this$date == null) {
                        if (other$date == null) {
                            break label59;
                        }
                    } else if (this$date.equals(other$date)) {
                        break label59;
                    }

                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResultBean;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $date = this.getDate();
        result = result * 59 + ($date == null ? 43 : $date.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResultBean(code=" + this.getCode() + ", msg=" + this.getMsg() + ", date=" + this.getDate() + ", data=" + this.getData() + ")";
    }
}
