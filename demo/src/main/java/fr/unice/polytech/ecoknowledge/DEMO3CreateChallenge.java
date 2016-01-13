package fr.unice.polytech.ecoknowledge;

/**
 * Created by Sébastien on 13/12/2015.
 */

import fr.unice.polytech.ecoknowledge.language.api.implem.Challenge;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;

public class DEMO3CreateChallenge {

    public static void main(String[] args){

        Challenge.create("Let it snow")
                .withIcon("https://s-media-cache-ak0.pinimg.com/originals/ee/f1/db/eef1db8f22e3d87f7ff0d05feac510fb.gif")
                .availableFrom(14,12,2015).to(27,12,2015)
                .repeatEvery(1, WEEK)
                .addLevel("Olaf")
                    .withImage("http://www.snut.fr/wp-content/uploads/2015/11/image-de-olaf-8.png")
                    .rewards(10)
                    .withConditions()
                        .valueOf("TMP").lowerThan(27).atLeast(90).percentOfTime()
                .addLevel("Anna")
                    .withImage("http://images6.fanpop.com/image/photos/35900000/Anna-frozen-35904954-1200-1200.png")
                    .rewards(20)
                    .withConditions()
                        .valueOf("TMP").lowerThan(24).atLeast(80).percentOfTime()
                        .and()
                        .valueOf("TMP").lowerThan(27)
                .addLevel("Elsa")
                    .withImage("http://static.yourtango.com/cdn/farfuture/v-AgwCZY0JnpWyYjML3sckixSiPe-IFuBh1UpPqt5VE/mtime:1405616243/sites/default/files/image_list/frozen_elsa_scared.gif")
                    .rewards(30)
                    .withConditions()
                        .valueOf("TMP").lowerThan(22).atLeast(80).percentOfTime()
                        .and()
                        .valueOf("TMP").lowerThan(24)
                .endChallenge();


    }

}
