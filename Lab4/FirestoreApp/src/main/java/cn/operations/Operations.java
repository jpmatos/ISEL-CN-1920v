package cn.operations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firestore.v1.Write;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Operations {
    private static Scanner sc = new Scanner(System.in);
    Firestore db;

    public Operations(Firestore db) {
        this.db = db;
    }

    public void getDocumentsGreaterIDByFreguesiaByEvent(String collectionName, int id, String freguesia, String eventType) throws ExecutionException, InterruptedException {
        FieldPath fpathFreguesia = FieldPath.of("location", "freguesia");
        FieldPath fpathEventType = FieldPath.of("event", "tipo");

        Query query = db.collection(collectionName)
                .whereGreaterThan("ID", id)
                .whereEqualTo(fpathFreguesia, freguesia)
                .whereEqualTo(fpathEventType, eventType);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            OcupacaoTemporaria ocup = doc.toObject(OcupacaoTemporaria.class);
            System.out.println(ocup);
        }
    }

    public void getDocumentByFreguesia(String collectionName, String freguesia) throws ExecutionException, InterruptedException {
        Query query = db.collection(collectionName).whereEqualTo("location.freguesia", freguesia);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            OcupacaoTemporaria ocup = doc.toObject(OcupacaoTemporaria.class);
            System.out.println(ocup);
        }
    }

    public void deleteDocumentIDFieldName(String collectionName, String ID, String fieldName) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collectionName).document(ID);
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put(fieldName, FieldValue.delete());
        ApiFuture<WriteResult> writeResult = docRef.update(updates);
        System.out.println("Update time: " + writeResult.get().getUpdateTime());
    }

    public void showDocumentByID(String collectionName, String ID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collectionName).document(ID);
        ApiFuture<DocumentSnapshot> docfut = docRef.get();
        DocumentSnapshot document = docfut.get();

        OcupacaoTemporaria ocup = document.toObject(OcupacaoTemporaria.class);
        System.out.println(ocup);
    }


    public void insertDocuments() throws Exception {

        System.out.println("Pathname:");
        String pathnameCSV = sc.next();

        System.out.println("Collection Name:");
        String collectionName = sc.next();

        BufferedReader reader = new BufferedReader(new FileReader(pathnameCSV));
        CollectionReference colRef = db.collection(collectionName);
        String line;
        while ((line = reader.readLine()) != null) {
            OcupacaoTemporaria ocup = convertLineToObject(line);
            DocumentReference docRef = colRef.document(ocup.ID + "");
            ApiFuture<WriteResult> result = docRef.set(ocup);
            WriteResult res = result.get();
            System.out.println(res);
        }
    }

    public OcupacaoTemporaria convertLineToObject (String line) throws ParseException {
        String[] cols = line.split(",");
        OcupacaoTemporaria ocup = new OcupacaoTemporaria();
        ocup.ID = Integer.parseInt(cols[0]);
        ocup.location = new Localizacao();
        ocup.location.point = new GeoPoint(Double.parseDouble(cols[1]), Double.parseDouble(cols[2]));
        ocup.location.coord = new Coordenadas();
        ocup.location.coord.X = Double.parseDouble(cols[1]);
        ocup.location.coord.Y = Double.parseDouble(cols[2]);
        ocup.location.freguesia = cols[3];
        ocup.location.local = cols[4];
        ocup.event = new Evento();
        ocup.event.evtID = Integer.parseInt(cols[5]);
        ocup.event.nome = cols[6];
        ocup.event.tipo = cols[7];
        ocup.event.details = new HashMap<String, String>();
        if (!cols[8].isEmpty()) ocup.event.details.put("Participantes", cols[8]);
        if (!cols[9].isEmpty()) ocup.event.details.put("Custo", cols[9]);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        ocup.event.dtInicio = formatter.parse(cols[10]);
        ocup.event.dtFinal = formatter.parse(cols[11]);
        ocup.event.licenciamento = new Licenciamento();
        ocup.event.licenciamento.code = cols[12];
        ocup.event.licenciamento.dtLicenc = formatter.parse(cols[13]);
        return ocup;
    }
}
