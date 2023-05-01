package kodlamaio.northwind.entities.concretes;


import kodlamaio.northwind.core.utilities.ProcessUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "process")
public class Process {
    static Logger logger = LoggerFactory.getLogger(ProcessUtil.class);

    @Id
    @Column(unique = true, nullable = false, name = "process_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long processId;

    @CreationTimestamp
    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    protected Date createdAt;

//    @Basic
//    @Column(name = "position")
//    @ColumnDefault("0")
//    private int position;

    @Basic
    @Column(name = "user_id")
    private String userId;

//    @Basic
//    @Column(name = "parent_process")
//    private Long parentProcess;

    @Basic
    @Column(name = "status")
    private String status;

//    @Basic
//    @Column(name = "exchange_name")
//    private String exchangeName;
//
//    @Basic
//    @Column(name = "routing_key")
//    private String routingKey;
//
//    @Basic
//    @Column(name = "parameters", columnDefinition="json")
//    private String parameters;

}

