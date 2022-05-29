package redcoder.chat.client.message.handler;

public interface Ordered {

   default int getOrder(){
       return 0;
   }

}
