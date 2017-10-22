package is.ru.hugb.calc;

import static spark.Spark.*;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class CalcWeb {
    public static void main(String[] args) {
        port(getHerokuPort());
        get("/", (req, res) -> {
            return "No route specified. Try /add/1,2";
        });
        get(
            "/add/:input",
            (req, res) -> add(req.params(":input"))
        );
        get(
            "/Trumpdaysleft",
            (req, res) -> "Days left in office: " + daysleft()
        );

    }

    static int getHerokuPort() {
        ProcessBuilder psb = new ProcessBuilder();
	      if (psb.environment().get("PORT") != null) {
	         return Integer.parseInt(psb.environment().get("PORT"));
	      }
        return 4567;
    }

    private static int add(String input) {
        StringCalculator Calculator = new StringCalculator();
        return Calculator.add(input);
    }

    public static String today() {
    DateTime today = new DateTime();
    return today.monthOfYear().getAsText();
    }
    public static String daysleft() {
    DateTime startDate = DateTime.now(); // now() : since Joda Time 2.0
    DateTime endDate = new DateTime(2021, 01, 20, 12, 0);

    Period period = new Period(startDate, endDate, PeriodType.dayTime());

    PeriodFormatter formatter = new PeriodFormatterBuilder()
        .appendDays().appendSuffix(" day ", " days ")
        .appendHours().appendSuffix(" hour ", " hours ")
        .appendMinutes().appendSuffix(" minute ", " minutes ")
        .appendSeconds().appendSuffix(" second ", " seconds ")
        .toFormatter();

    return formatter.print(period);
    }
}
