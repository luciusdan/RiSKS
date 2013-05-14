/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.Date;

/**
 *
 * @author Dirk
 */
public class Loot {
    private Member member;
    private Date Timestamp;
    private String Name;

    public Loot(Date Timestamp,Member member, String Name) {
        this.Timestamp = Timestamp;
        this.member = member;
        this.Name = Name;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public Date getTimestamp() {
        return Timestamp;
    }

    public String getName() {
        return Name;
    }

    public void setTimestamp(Date Timestamp) {
        this.Timestamp = Timestamp;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    
}
