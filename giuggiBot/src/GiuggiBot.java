import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class GiuggiBot extends TelegramLongPollingBot {

        private final Crawler crawler = new Crawler();
        private DB database;
        public GiuggiBot()
        {
            database = new DB();
        }

        public String getBotUsername() {
            return "giuggiBot";
        }
        @Override
        public String getBotToken() {
            return "6721463225:AAGoJymD_bnHKMWKYNuckzJRWH6rSLpRBs4";
        }
        public void onUpdateReceived(Update update) {
            String msg = update.getMessage().getText();
            String messaggio = "";

            if(msg.startsWith("/start"))
            {
                messaggio = "LISTA COMANDI:\n/classifica --> mostra i videogiochi del momento\n/add --> aggiungi un gioco alla tua wishList\n/remove --> rimuovi un brano dalla tua wishList\n/wishList --> mostra la tua wishList con i giochi inseriti";
            }

            if(msg.startsWith("/classifica"))
            {
                messaggio = Crawler.CrawlerGames();
            }
            if(msg.startsWith("/add"))
            {
                database.aggiungiGioco(update.getMessage().getChatId().toString(),msg.substring(5));
                messaggio="gioco aggiunto";
            }
            if(msg.startsWith("/remove"))
            {
                database.EliminaGioco(update.getMessage().getChatId().toString(),msg.substring(8));
                messaggio="gioco rimosso";
            }
            if(msg.startsWith("/wishList"))
            {
                messaggio= database.raccogliDati(update.getMessage().getChatId().toString()).toString();
            }

            String chatId=update.getMessage().getChatId().toString();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(messaggio);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                // gestione errore in invio
            }
        }
    }
