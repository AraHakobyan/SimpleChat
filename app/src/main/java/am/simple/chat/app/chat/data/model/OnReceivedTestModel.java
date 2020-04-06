package am.simple.chat.app.chat.data.model;

/**
 * Created by Ara Hakobyan on 4/5/2020.
 * Company IDT
 */
public class OnReceivedTestModel
{

    public OnReceivedTestModel(OnTestMessageModel data){
        messageId = data.id;
        userId = data.userId;
    }

    public String messageId;
    public int userId;
}