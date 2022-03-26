package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static platform.StaticVariables.FORMATTER;
import static platform.StaticVariables.NO_RESTRICTION;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CodeObject {
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String code = "";

    @Column
    private String date;

    @JsonIgnore
    private LocalDateTime dateNano;

    @Column
    private long time;

    @Column
    private long views;

    @JsonIgnore
    private boolean restrictedByView;
    @JsonIgnore
    private boolean restrictedByTime;


    void calcRestTime() {
        this.time = restrictedByTime ? Duration.between(LocalDateTime.now(),
                LocalDateTime.parse(this.date, FORMATTER).plusSeconds(this.time)).toSeconds() : NO_RESTRICTION;
    }

    void calcRestViews() {
        this.views = restrictedByView ? --this.views : NO_RESTRICTION;
    }

    boolean isExpired() {
        return (this.views < 0 && restrictedByView) || (this.time < 0 && restrictedByTime);
    }
}
