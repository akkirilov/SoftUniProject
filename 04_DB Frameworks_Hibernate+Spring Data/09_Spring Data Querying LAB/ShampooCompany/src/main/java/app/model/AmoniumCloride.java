package app.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "amonium_cloride")
@PrimaryKeyJoinColumn(name = "id")
public class AmoniumCloride extends BasicChemicatIngredient {

    public AmoniumCloride() {
        super("Amonium Cloride", BigDecimal.valueOf(1.2), "NH4CL");
    }
    
}
