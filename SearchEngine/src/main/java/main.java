import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.elasticsearch.action.NotifyOnceListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main {
    public static void main(String[] args) throws FileNotFoundException, IOException {

        URL resource = main.class.getResource("princesssuvinand6dwarf-firebase-adminsdk-ef7wr-e31589d527.json");


        //FileInputStream serviceAccount = new FileInputStream("sw-project-53154-firebase-adminsdk-26ska-e9c724aced.json");
        InputStream inputStream = resource.openStream();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .setDatabaseUrl("https://princesssuvinand6dwarf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                // Or other region, e.g. <databaseName>.europe-west1.firebasedatabase.app
                .build();
        FirebaseApp.initializeApp(options);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("shop_name_search");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                System.out.println("자식 추가 발생 감지");
                System.out.println(snapshot.getValue().toString());

                String name = snapshot.getValue().toString();
                String cutName = name.substring(6, name.length() -1);

                Map<String, String> map = new HashMap<>();
                map.put("name", cutName);

                Rest rest = new Rest();
                //List<Map<String, Object>> shopList = rest.start(cutName);
                List<String> shopList = rest.start(cutName);

                DatabaseReference dr = snapshot.getRef();
                Return myReturn = new Return(shopList);

                int index = 0;
                for(String tmp : shopList){
                    dr.child("result").setValue(shopList, null);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SleepThread sleepThread = new SleepThread();
        sleepThread.start();
    }
}
