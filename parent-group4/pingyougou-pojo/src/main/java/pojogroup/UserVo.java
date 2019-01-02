package pojogroup;

import java.io.Serializable;
import java.util.Date;

public class UserVo implements Serializable {

    private Long id;

    private String nickName;

    private String headPic;

    private String sex;

    private Date birthday;

    private String uadress;

    private String job;

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", headPic='" + headPic + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", uadress='" + uadress + '\'' +
                ", job='" + job + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUadress() {
        return uadress;
    }

    public void setUadress(String uadress) {
        this.uadress = uadress;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
