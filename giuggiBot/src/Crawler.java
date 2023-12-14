import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

    /*public static void crawl(String url, ArrayList<String> urlVisited){



        Document doc = null;
        doc = request(url, urlVisited);
        if(doc != null) {
           //Devo salvarmi la tabella con le classifiche
            Elements tr = doc.select("table.hm-table.hm-table-classify").select("tr");
            //Una volta trovata la TABUELLA dobbiamo analizzare ciò che c'è dentro

            for(Element trs : tr){
                //Per ogni tr dovrei analizzare ciò che c'è dentro, nel tuo caso un h3 e un a[href]
                //utilizza sempre il .select
            }

        }
    }

    private static Document request(String url, ArrayList<String> urlvisited) {

        Connection conn = Jsoup.connect(url);
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (conn.response().statusCode() == 200) {
            urlvisited.add(url);

            return doc;
        }
        return null;
    }*/

    public static String CrawlerGames()
    {
        String url = "https://www.ludomedia.it/novita/videogiochi-del-momento";
        try
        {
            Document doc = Jsoup.connect(url).get();
            Elements giochi = doc.select("h2.titolo").select("a.pagApriAsync");
            String result = "Videogiochi più richiesti: \n";

            int a = 0;
            for(Element gioco : giochi)
            {
                a++;
                String titolo = gioco.text();
                result += a+"  "+titolo+"\n";
            }
            return result;
        }
        catch (IOException x)
        {
            x.printStackTrace();
        }
        return null;
    }
}
