package kodlamaio.northwind.entities.concretes;

//import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Data
@Entity
@Table(name="userDb")
@AllArgsConstructor
@NoArgsConstructor
//@TypeDefs({
//        @TypeDef(
//                name = "string-array",
//                typeClass = StringArrayType.class
//        )
//})
public class User {

    /** The id. */
    @Id
    @Column(unique = true, nullable = false, name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The user name. */
    @Column(unique = true, nullable = false, name = "user_name")
    private String userName;

    /** The email. */
    @Column(unique = true, name = "user_mail")
    private String email;

//    /** The contact_list. */
//    @Column(unique = true, name = "contact_list")
//    private List<String> ContactList;

    /** The password. */
    @Column(name = "password", length = 60)
    private String password;

//    @Type( type = "string-array" )
//    @Column(
//            name = "contact_list",
//            columnDefinition = "text[]"
//    )
//    private String[] contactList;

//    @Column(name = "contact_list", columnDefinition = "text[]")
//    @Type(type = "com.baeldung.hibernate.arraymapping.CustomStringArrayType")
//    private String[] contactList;

//    @Type(type = "list-array")
//    @Column(
//            name = "contact_list",
//            columnDefinition = "text[]"
//    )
//    private List<String> contactList;
//    /** The enabled. */
//    private boolean enabled;

//    /** The registration date. */
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date registrationDate;
////
////    /** The last activity date. */
//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastActivityDate;



//    /** The locked. */
//    private boolean locked;

//    /** The roles. */
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "contact_list", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"))
////    private Collection<Role> roles;
//    private List<String> tags = new ArrayList<>();
//    private Set<String> contacts;
//    public Set getContacts() {
//        return contacts;
//    }
//
//    public void setSubjects(Set contacts) {
//        this.contacts = contacts;
//    }
    /**
     * Instantiates a new user.
     */
//    public User() {
//        super();
//        this.enabled = false;
//    }

    /**
     * Sets the last activity date.
     */
//    @PreUpdate
//    public void setLastActivityDate() {
//        setLastActivityDate(new Date());
//    }

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

//    @Column(name="category_id")
//    private int categoryId;

    @Column(name="user_name")
    private String userName;

    @Column(name="password")
    private String password;

//    @Column(name="unit_price")
//    private double unitPrice;
//
//    @Column(name="units_in_stock")
//    private short unitsInStock;
//
//    @Column(name="quantity_per_unit")
//    private String quantityPerUnit;
*/

}
