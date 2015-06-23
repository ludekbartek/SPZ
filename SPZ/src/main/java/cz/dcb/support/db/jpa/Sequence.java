package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.String;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sequence")
public class Sequence implements Serializable {

    @Column(name="seq_count",table="sequence",nullable=false)
    @Basic
    private int seqCount;
    @Column(name="seq_name",table="sequence",nullable=false,length=32)
    @Id
    private String seqName;

    public Sequence() {

    }
   
    public int getSeqCount() {
        return this.seqCount;
    }

    public void setSeqCount(int seqCount) {
        this.seqCount = seqCount;
    }
   
    public String getSeqName() {
        return this.seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }
}
