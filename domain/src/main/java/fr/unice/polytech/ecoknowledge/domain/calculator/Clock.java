package fr.unice.polytech.ecoknowledge.domain.calculator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class Clock {

    private String middlewareTimeZone = "Europe/Paris";
    private DateTime time;

    public Clock() {
        time = null;
    }

    public void setMiddlewareTimeZone(String ID){
        middlewareTimeZone = ID;
    }

    public void setTime(String time){

        this.time = createDate(time);

    }

    public DateTime createDate(String time){

        try {
           /* SimpleDateFormat day = new SimpleDateFormat("YYYY-MM-dd");
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");

            String dayS = time.split("T")[0];
            String hourS = time.split("T")[1].split("Z")[0];

            LocalDate lDate = LocalDate.fromDateFields(day.parse(dayS));
            LocalTime lTime = LocalTime.fromDateFields(hour.parse(hourS));
*/
            DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss'Z'");
            DateTime dt = dtf.parseDateTime(time);
            System.out.println("Date built from input : " + dt.toString(DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss'Z'")));
            //new LocalDate(day.parse(dayS));

            DateTime dateTimeInProperTZ = dt.toDateTime(DateTimeZone.forID(middlewareTimeZone));
            System.out.println("Date built with TZ :\t" + dateTimeInProperTZ.toString(DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss'Z'")));



            return dateTimeInProperTZ;
        }
        catch (Throwable t){
            System.err.println(t);
            return null;
        }

    }

    public DateTime getTime() {
        return time==null? DateTime.now():time;
    }
}
