import fr.unice.polytech.ecoknowledge.language.api.implem.Challenge;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class BLOBURP {

    public static void main(String[] args){

        Challenge.create("TEST")
                .withIcon("jklmqdfsjùoifqs")
                .availableFrom(2).to(3)
                .during(2, DURATION_TYPE.WEEK)
                .atLevel("LEVEL1")
                    .rewards(2)
                    .withConditions()
                        .valueOf("SMTH").greaterThan(23)
                        .and()
                        .increase("SMTH2").by(2).percent().comparedTo(OLD_PERIOD.LAST_MONTH)
                .atLevel("LEVEL2")
                    .rewards(3)
                    .withConditions()
                        .valueOf("SMTH").greaterThan(25)
                        .and()
                        .increase("SMETH2").by(4).percent().comparedTo(OLD_PERIOD.LAST_MONTH)
                .end();

    }

}
