package app.com.example.wagner.meupredi.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Model.ModelClass.AgendaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by wagne on 31/03/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "BancoUpd";


    // ---------- TABLE PACIENTES ----------
    // BANCO DE PACIENTES
    private static final String TABLE_PACIENTES = "pacientes";

    // COLUNAS DO BANCO DE PACIENTES
    private static final String KEY_ID = "idAccount";
    private static final String KEY_NOME = "nome";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SEXO = "sexo";
    private static final String KEY_NASCIMENTO = "nascimento";
    private static final String KEY_IDADE = "idade";
    private static final String KEY_CIRCUNFERENCIA = "circunferencia";
    private static final String KEY_ALTURA = "altura";
    private static final String KEY_IMC = "imc";
    private static final String KEY_ULTIMADICA = "ultimaDica";

    // ---------- TABLE PESOS ----------
    // BANCO DE PESOS (LINKADO AO BANCO DE PACIENTES POR ID)
    private static final String TABLE_PESOS = "pesos";

    // COLUNAS DO BANCO DE PESOS
    private static final String KEY_ID_PESO = "idPeso";
    private static final String KEY_PESO = "peso";
    private static final String KEY_DATA = "dataPeso";
    private static final String KEY_CIRCUNFERENCIAPESO = "circunferencia";
    private static final String KEY_FLAGPESO = "flagPeso";
    private static final String KEY_PAC = "pac";

    // ---------- TABLE EXAMES ----------
    // TABLE EXAMES
    private static final String TABLE_EXAMES = "exames";

    //COLUNAS DOS EXAMES
    private static final String KEY_ID_EXAME = "idExame";
    private static final String KEY_GLICOSEJEJUM = "glicosejejum";
    private static final String KEY_GLICOSE75G = "glicose75g";
    private static final String KEY_COLESTEROL = "colesterol";
    private static final String KEY_DATA_EXAME = "dataExame";
    private static final String KEY_PAC2 = "pac2";
    private static final String KEY_FLAGTAXA = "flagTaxa";
    private static final String KEY_HEMOGLOBINAGLICO = "hemoglobinaglico";

    // ---------- TABLE LIPIDOGRAMA ----------
    //TABLE LIPIDOGRAMA
    private static final String TABLE_LIPIDOGRAMA = "lipidograma";

    //COLUNAS DO LIPIDOGRAMA
    private static final String KEY_ID_LIPIDOGRAMA = "idLipidograma";
    private static final String KEY_HDL = "lipHdl";
    private static final String KEY_LDL = "lipLdl";
    private static final String KEY_COLESTEROLTOTAL = "lipColesterolTotal";
    private static final String KEY_TRIGLICERIDES = "lipTriglicerides";
    private static final String KEY_DATA_LIPIDOGRAMA = "dataLipidograma";
    private static final String KEY_LOCAL_LIPIDOGRAMA = "localLipidograma";
    private static final String KEY_PAC4 = "pac4";

    // ---------- TABLE HEMOGRAMA ----------
    //TABLE HEMOGRAMA
    private static final String TABLE_HEMOGRAMA = "hemograma";

    //COLUNAS HEMOGRAMA
    private static final String KEY_ID_HEMOGRAMA = "idHemograma";
    private static final String KEY_HEMOGLOBINA = "hemHemoglobina";
    private static final String KEY_HEMATOCRITO = "hemHematocrito";
    private static final String KEY_VGM = "hemVgm"; // Vol Glob Medio
    private static final String KEY_CHCM = "hemChcm"; // Hem Glob Media
    private static final String KEY_CHGM = "hemChgm"; // C.H Glob Media
    private static final String KEY_RWD = "hemRwd";
    private static final String KEY_DATA_HEMOGRAMA = "dataHemograma";
    private static final String KEY_PAC5 = "pac5";

    // --------- TABLE AGENDA ---------
    private static final String TABLE_AGENDA = "agenda";

    private static final String KEY_ID_AGENDA = "idAgenda";
    private static final String KEY_DATA_AGENDA = "dataAgenda";
    private static final String KEY_TIME_AGENDA = "timeAgenda";
    private static final String KEY_TITULO_AGENDA = "tituloAgenda";
    private static final String KEY_LUGAR_AGENDA = "lugarAgenda";
    private static final String KEY_PAC6_AGENDA = "pac6";


       // KEY_PAC -> Chave estrangeira da tabela PESOS
       // KEY_PAC2 -> Chave estrangeira da tabela EXAMES
       // KEY_PAC3 -> Chave estrangeira da tebal EXERCICIOS
       //   Todas referenciando a tabela paciente.


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  CREATE_PACIENTES_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_PACIENTES
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NOME + " TEXT,"
                + KEY_SENHA + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_SEXO + " TEXT,"
                + KEY_NASCIMENTO + " TEXT,"
                + KEY_IDADE + " INTEGER,"
                + KEY_CIRCUNFERENCIA + " REAL,"
                + KEY_ALTURA + " REAL,"
                + KEY_IMC + " REAL,"
                + KEY_ULTIMADICA + " INTEGER"
                + ")";
        db.execSQL(CREATE_PACIENTES_TABLE);

        String  CREATE_PESOS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_PESOS
                + "("
                + KEY_ID_PESO + " INTEGER PRIMARY KEY,"
                + KEY_PESO + " REAL,"
                + KEY_DATA + " DATETIME,"
                + KEY_CIRCUNFERENCIAPESO + " REAL,"
                + KEY_FLAGPESO + " INTEGER,"
                + KEY_PAC + " INTEGER,"
                + " FOREIGN KEY("+KEY_PAC+") REFERENCES "+TABLE_PACIENTES+"("+KEY_ID+"));";
        db.execSQL(CREATE_PESOS_TABLE);

        String CREATE_EXAMES_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_EXAMES
                + "("
                + KEY_ID_EXAME + " INTEGER PRIMARY KEY,"
                + KEY_GLICOSE75G + " REAL,"
                + KEY_GLICOSEJEJUM + " REAL,"
                + KEY_COLESTEROL + " REAL,"
                + KEY_DATA_EXAME + " DATETIME,"
                + KEY_PAC2 + " INTEGER,"
                + KEY_HEMOGLOBINAGLICO + " REAL,"
                + KEY_FLAGTAXA + " INTEGER,"
                + " FOREIGN KEY("+KEY_PAC2+") REFERENCES "+TABLE_PACIENTES+"("+KEY_ID+"));";
        db.execSQL(CREATE_EXAMES_TABLE);

        String CREATE_LIPIDOGRAMA_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_LIPIDOGRAMA
                + "("
                + KEY_ID_LIPIDOGRAMA + " INTEGER PRIMARY KEY,"
                + KEY_HDL + " INTEGER,"
                + KEY_LDL + " INTEGER,"
                + KEY_COLESTEROLTOTAL + " INTEGER,"
                + KEY_TRIGLICERIDES + " INTEGER,"
                + KEY_DATA_LIPIDOGRAMA + " DATETIME,"
                + KEY_LOCAL_LIPIDOGRAMA + " TEXT,"
                + KEY_PAC4 + " INTEGER,"
                + " FOREIGN KEY ("+KEY_PAC4+") REFERENCES "+TABLE_PACIENTES+"("+KEY_ID+"));";
        db.execSQL(CREATE_LIPIDOGRAMA_TABLE);

        String CREATE_HEMOGRAMA_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_HEMOGRAMA
                + "("
                + KEY_ID_HEMOGRAMA + " INTEGER PRIMARY KEY,"
                + KEY_HEMOGLOBINA + " REAL,"
                + KEY_HEMATOCRITO + " REAL,"
                + KEY_VGM + " REAL,"
                + KEY_CHCM + " REAL,"
                + KEY_CHGM + " REAL,"
                + KEY_RWD + " REAL,"
                + KEY_DATA_HEMOGRAMA + " DATETIME,"
                + KEY_PAC5 + " INTEGER,"
                + " FOREIGN KEY ("+KEY_PAC5+") REFERENCES "+TABLE_PACIENTES+"("+KEY_ID+"));";
        db.execSQL(CREATE_HEMOGRAMA_TABLE);

        String CREATE_AGENDA_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_AGENDA
                + "("
                + KEY_ID_AGENDA + " INTEGER PRIMARY KEY,"
                + KEY_TITULO_AGENDA + " TEXT,"
                + KEY_LUGAR_AGENDA + " TEXT,"
                + KEY_DATA_AGENDA + " TEXT,"
                + KEY_TIME_AGENDA + " TEXT,"
                + KEY_PAC6_AGENDA + " INTEGER,"
                + " FOREIGN KEY ("+KEY_PAC6_AGENDA+") REFERENCES "+TABLE_PACIENTES+"("+KEY_ID+"));";
        db.execSQL(CREATE_AGENDA_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PESOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIPIDOGRAMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEMOGRAMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENDA);

        onCreate(db);
    }

    public String modelAddDate(Paciente paciente, AgendaClass agenda){
        Log.d("Paciente : ", paciente.getNome());
        Log.d("Evento : ", agenda.getTitulo());

        int idPaciente = paciente.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITULO_AGENDA, agenda.getTitulo());
        values.put(KEY_LUGAR_AGENDA, agenda.getLocal());
        values.put(KEY_DATA_AGENDA, agenda.getDate());
        values.put(KEY_TIME_AGENDA, agenda.getTime());
        values.put(KEY_PAC6_AGENDA, paciente.getId());

        long retorno;
        retorno = db.insert(TABLE_AGENDA, null, values);

        if(retorno == -1){
            return "Erro ao inserir o registro da agenda!";
        } else {
            return "Evento inserido com sucesso na agenda!";
        }
    }

    public ArrayList<AgendaClass> modelGetAllAgendas (Paciente paciente){
        ArrayList<AgendaClass> agendaList = new ArrayList<>();
        int idPaciente = paciente.getId();

        String selectQuery = "SELECT * FROM " + TABLE_AGENDA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(5) != null){
                    if(Integer.parseInt(cursor.getString(5)) == idPaciente){
                        String local = cursor.getString(2);
                        String date = cursor.getString(3);
                        String time = cursor.getString(4);
                        Log.d("Pegando local : " , local);

                        AgendaClass agendaClass = new AgendaClass(cursor.getString(1), local, date, time);
                        agendaList.add(agendaClass);
                    }
                }
            } while(cursor.moveToNext());
        }

        return agendaList;
    }
/*
    public String modelAddExame (Taxas exame){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Log.d("Adicionando : ", "Metodo addExame");
        Log.d("Glicose 75g : ", String.valueOf(exame.getGlicose75g()));
        Log.d("Glicose jejum : ", String.valueOf(exame.getGlicoseJejum()));
        Log.d("Colesterol : ", String.valueOf(exame.getColesterol()));

        values.put(KEY_GLICOSE75G, exame.getGlicose75g());
        values.put(KEY_GLICOSEJEJUM, exame.getGlicoseJejum());
        values.put(KEY_COLESTEROL, exame.getColesterol());
        values.put(KEY_DATA_EXAME, String.valueOf(exame.getDataExame()));
        values.put(KEY_HEMOGLOBINAGLICO, exame.getHemoglobinaGlico());
        values.put(KEY_PAC2, exame.getIdPac());

        long retorno;
        retorno = db.insert(TABLE_EXAMES, null, values);
        db.close();

        if(retorno == -1){
            return "Erro ao inserir o registro dos exames!";
        } else {
            return "Registro dos exames inserido com sucesso!";
        }

    }
*/
    //metodo chamado na classe CriarConta para adicionar um novo paciente ao banco
    public String modelAddPaciente(Paciente paciente){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //DEBUG: imprime dados do objeto paciente
        Log.d("Adicionando: ", "método addPaciente");
        Log.d("Nome : ", paciente.getNome());
        Log.d("Senha : ", paciente.getSenha());
        Log.d("Email: ", paciente.getEmail());
        Log.d("Sexo: ", String.valueOf(paciente.getSexo()));
        Log.d("Nascimento: ", paciente.getNascimento());
        Log.d("Idade : ", String.valueOf(paciente.getIdade()));
        Log.d("Circunferencia : ", String.valueOf(paciente.getCircunferencia()));
        Log.d("Peso : ", String.valueOf(paciente.getPeso()));
        Log.d("Altura : ", String.valueOf(paciente.getAltura()));
        Log.d("IMC : ", String.valueOf(paciente.getImc()));
        Log.d("GlicoseJejum : ", String.valueOf(paciente.getGlicoseJejum()));
        Log.d("Glicose75g : ", String.valueOf(paciente.getGlicose75g()));
        Log.d("Colesterol : ", String.valueOf(paciente.getColesterol()));

        //agrupa dados e insere no banco
        values.put(KEY_NOME, paciente.getNome());
        values.put(KEY_SENHA, paciente.getSenha());
        values.put(KEY_EMAIL, paciente.getEmail());
        values.put(KEY_SEXO, paciente.getSexo());
        values.put(KEY_NASCIMENTO, String.valueOf(paciente.getNascimento()));
        values.put(KEY_IDADE, paciente.getIdade());
        values.put(KEY_CIRCUNFERENCIA, paciente.getCircunferencia());
        values.put(KEY_ALTURA, paciente.getAltura());
        values.put(KEY_IMC, paciente.getImc());
        values.put(KEY_ULTIMADICA, paciente.getUltimaDica());

        long retorno;
        retorno = db.insert(TABLE_PACIENTES, null, values);
        db.close();

        if(retorno == -1){
            return "Erro ao inserir o registro!";
        } else {
            return "Registro inserido com sucesso!";
        }
    }

    //metodo chamado na classe TelaLogin para DEBUG
    public List<Paciente> modelGetAllUsers(){

        List<Paciente> pacientesList = new ArrayList<Paciente>();

        //pega todos os dados do banco de pacientes
        String selectQuery = "SELECT * FROM " + TABLE_PACIENTES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //adiciona, um por um, a uma lista
        if(cursor.moveToFirst()){
            do{
                Paciente paciente = new Paciente();
                paciente.setId(Integer.parseInt(cursor.getString(0)));
                paciente.setNome(cursor.getString(1));
                paciente.setSenha(cursor.getString(2));
                paciente.setEmail(cursor.getString(3));
                paciente.setSexo(cursor.getString(4));
                paciente.setNascimento(cursor.getString(5));
                paciente.setIdade(Integer.parseInt(cursor.getString(6)));
                paciente.setCircunferencia(Double.parseDouble(cursor.getString(7)));
                paciente.setAltura(Double.parseDouble(cursor.getString(8)));
                paciente.setImc(Double.parseDouble(cursor.getString(9)));
                paciente.setUltimaDica(Integer.parseInt(cursor.getString(10)));

                //pega seu ultimo peso registrado
                paciente.setPeso(modelGetPeso(paciente));

                //pega suas ultimas taxas cadastradas
                //paciente = modelGetUltimasTaxas(paciente);

                pacientesList.add(paciente);

            }while(cursor.moveToNext());
        }
        return pacientesList;
    }
/*
    public List<Taxas> modelGetAllExames() throws ParseException {
        List<Taxas> exameList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_EXAMES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Taxas exame = new Taxas();
                exame.setId(Integer.parseInt(cursor.getString(0)));
                exame.setGlicose75g(Double.parseDouble(cursor.getString(1)));
                exame.setGlicoseJejum(Double.parseDouble(cursor.getString(2)));
                exame.setDataExame(cursor.getString(4));
                exame.setIdPac(Integer.parseInt(cursor.getString(5)));
                exame.setHemoglobinaGlico(Double.parseDouble(cursor.getString(6)));
                exameList.add(exame);
            }while(cursor.moveToNext());
        }
        return exameList;
    }
*/
    //metodo chamado na classe MenuPrincipal para manter o objeto 'paciente' sempre atualizado
    public Paciente modelGetPaciente(String email) {

        //pega todos os pacientes
        List<Paciente> pacientesList = modelGetAllUsers();
        Paciente paciente = new Paciente();

        //procura paciente desejado
        for(int i = 0; i < pacientesList.size(); i++) {
            if(email.equals(pacientesList.get(i).getEmail())) {
                paciente = pacientesList.get(i);
                break;
            }
        }

        return paciente;
    }

    public void modelDeletAllPacientes() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "DELETE FROM " + TABLE_PACIENTES;

        db.execSQL(selectQuery);
        db.close();
    }

    //metodo chamado na classe TelaLogin para verificar as credenciais do usuario
    public Paciente modelVerificarLogin(String email, String senha){

        //pega todos os pacientes
        String selectQuery = "SELECT * FROM " + TABLE_PACIENTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Paciente paciente = new Paciente();

        //verifica as credenciais
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(3).equals(email) && cursor.getString(2).equals(senha)){
                    paciente.setId(Integer.parseInt(cursor.getString(0)));
                    paciente.setNome(cursor.getString(1));
                    paciente.setSenha(cursor.getString(2));
                    paciente.setEmail(cursor.getString(3));
                    paciente.setSexo(cursor.getString(4));
                    paciente.setNascimento(cursor.getString(5));
                    paciente.setIdade(Integer.parseInt(cursor.getString(6)));
                    paciente.setCircunferencia(Double.parseDouble(cursor.getString(7)));
                    paciente.setAltura(Double.parseDouble(cursor.getString(8)));
                    paciente.setImc(Double.parseDouble(cursor.getString(9)));
                    paciente.setUltimaDica(Integer.parseInt(cursor.getString(10)));

                    //DEBUG
                    Log.d("Infos do banco: ", "verificarLogin");
                    Log.d("Nome : ", paciente.getNome());
                    Log.d("Senha : ", paciente.getSenha());
                    Log.d("Email: ", paciente.getEmail());
                    Log.d("Sexo: ", String.valueOf(paciente.getSexo()));
                    Log.d("Nascimento: ", paciente.getNascimento());
                    Log.d("Idade : ", String.valueOf(paciente.getIdade()));
                    Log.d("Circunferencia : ", String.valueOf(paciente.getCircunferencia()));
                    Log.d("Peso : ", String.valueOf(paciente.getPeso()));
                    Log.d("Altura : ", String.valueOf(paciente.getAltura()));
                    Log.d("IMC : ", String.valueOf(paciente.getImc()));
                    Log.d("GlicoseJejum : ", String.valueOf(paciente.getGlicoseJejum()));
                    Log.d("Glicose75g : ", String.valueOf(paciente.getGlicose75g()));
                    Log.d("Colesterol : ", String.valueOf(paciente.getColesterol()));

                    //se encontrou o paciente correto, retorna objeto
                    return paciente;
                }

            }while(cursor.moveToNext());

        }
        paciente.setId(-1);
        return paciente;
    }

    //metodo chamado na classe EsqueceuSenha para verificar existencia do email no banco
    public Paciente modelVerificarEmail(String email) {

        String selectQuery = "SELECT * FROM " + TABLE_PACIENTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Paciente paciente = new Paciente();

        //verifica se existe algum usuario com o mesmo email
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(3).equals(email)){
                    paciente.setId(Integer.parseInt(cursor.getString(0)));
                    paciente.setNome(cursor.getString(1));
                    paciente.setSenha(cursor.getString(2));

                    //se existir, retorna id do mesmo
                    return paciente;
                }

            }while(cursor.moveToNext());
        }
        //se nao existir, retorna objeto com id igual a -1
        paciente.setId(-1);
        return paciente;
    }

    //metodo chamado na classe PosLogin para atualizar dados do paciente no banco
    public boolean modelAtualizarPaciente(Paciente paciente){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        String where =  this.KEY_ID + "=" + String.valueOf(paciente.getId());

        values = new ContentValues();

        values.put(KEY_NOME, paciente.getNome());
        values.put(KEY_SENHA, paciente.getSenha());
        values.put(KEY_EMAIL, paciente.getEmail());
        values.put(KEY_SEXO, paciente.getSexo());
        values.put(KEY_NASCIMENTO, paciente.getNascimento());
        values.put(KEY_IDADE, paciente.getIdade());
        values.put(KEY_CIRCUNFERENCIA, paciente.getCircunferencia());
        values.put(KEY_ALTURA, paciente.getAltura());
        values.put(KEY_IMC, paciente.getImc());
        values.put(KEY_ULTIMADICA, paciente.getUltimaDica());

        return db.update(this.TABLE_PACIENTES, values, where, null) > 0;

    }
/*
    public boolean modelEditPeso(PesoClass pesoClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        String where = this.KEY_ID_PESO + "=" + String.valueOf(pesoClass.getIdPeso());

        values = new ContentValues();
        values.put(KEY_PESO, pesoClass.getPeso());
        values.put(KEY_CIRCUNFERENCIAPESO, pesoClass.getCircunferencia());

        return db.update(this.TABLE_PESOS, values, where, null) > 0;
    }

    public boolean modelEditExame(Taxas taxas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        String where = this.KEY_ID_EXAME + "=" + String.valueOf(taxas.getId());

        values = new ContentValues();
        values.put(KEY_GLICOSE75G, taxas.getGlicose75g());
        values.put(KEY_GLICOSEJEJUM, taxas.getGlicoseJejum());
        values.put(KEY_HEMOGLOBINAGLICO, taxas.getHemoglobinaGlico());

        return db.update(this.TABLE_EXAMES, values, where, null) > 0;
    }
*/

    //metodo chamado na classe PosLogin e Peso para registrar peso do paciente
    public void modelAtualizarPeso(Paciente paciente){

        //pega data atual
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        Log.d("Data : ", dateString);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;

        //guarda dados do paciente (peso e data atual)
        values = new ContentValues();
        values.put(KEY_PESO, paciente.getPeso());
        values.put(KEY_DATA, dateString);
        values.put(KEY_CIRCUNFERENCIAPESO, paciente.getCircunferencia());
        values.put(KEY_FLAGPESO, 0);
        values.put(KEY_PAC, paciente.getId());

        //insere dados no banco de pesos
        long retorno;
        retorno = db.insert(TABLE_PESOS, null, values);
        db.close();

        if(retorno == -1){
            Log.d("Erro ao atualizar peso!", "DatabaseHandler");
        } else {
            Log.d("Peso atualizado!", "DatabaseHandler");
        }
    }


    //metodo chamado na classe TelaLogin para pegar o peso atual do paciente
    public double modelGetPeso (Paciente paciente){
        int id = paciente.getId();
        double peso = 0;

        String selectQuery = "SELECT * FROM " + TABLE_PESOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        //procura o peso pelo id do paciente
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(5).equals(String.valueOf(id)) &&
                 cursor.getString(4).equals(String.valueOf(0))){
                    peso = Double.parseDouble(cursor.getString(1));
                    Log.d("Peso achado : ", String.valueOf(peso));
                }
            } while(cursor.moveToNext());
        }

        //retorna peso atual (ou 0 se nao encontrou/ainda nao cadastrou)
        return peso;
    }

    public double modelGetCircunferencia(Paciente paciente){
        int id = paciente.getId();
        String selectQuery = "SELECT * FROM " + TABLE_PESOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        double circunferencia = 0;

        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(3).equals(String.valueOf(id)) &&
                        cursor.getString(4).equals(String.valueOf(0))){
                    circunferencia = Double.parseDouble(cursor.getString(3));
                }
            } while(cursor.moveToNext());
        }
        return circunferencia;
    }

    public ArrayList<Float> modelGetAllPesos(Paciente paciente){
        int idPaciente = paciente.getId();
        ArrayList<Float> pesos = new ArrayList<>();
        Log.d("DB, ", "GetAllPesos");

        String selectQuery  = "SELECT * FROM " + TABLE_PESOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                if(Integer.parseInt(cursor.getString(5))==idPaciente &&
                        cursor.getString(4).equals(String.valueOf(0))){
                    pesos.add(Float.valueOf(cursor.getString(1)));
                }
            } while(cursor.moveToNext());
        }
        return pesos;
    }
/*
    public ArrayList<PesoClass> modelGetAllPesoClass(Paciente paciente){
        int idPaciente = paciente.getId();
        ArrayList<PesoClass> pesos = new ArrayList<>();
        Log.d("DB, ", "GetAllPesos");

        String selectQuery  = "SELECT * FROM " + TABLE_PESOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                if(Integer.parseInt(cursor.getString(5))==idPaciente &&
                        cursor.getString(4).equals(String.valueOf(0))){
                    PesoClass aux = new PesoClass(Integer.parseInt(cursor.getString(0))
                    , Double.parseDouble(cursor.getString(1)),
                            Double.parseDouble(cursor.getString(3)),
                            cursor.getString(2),
                            0, Integer.parseInt(cursor.getString(5)));
                    //pesos.add(Float.valueOf(cursor.getString(1)));
                    pesos.add(aux);
                }
            } while(cursor.moveToNext());
        }
        return pesos;
    }
*/
/*
    public ArrayList<Taxas> modelGetAllExameClass(Paciente paciente) throws Exception{
        int idPaciente = paciente.getId();
            ArrayList<Taxas> exames = new ArrayList<>();

            String selectQuery = "SELECT * FROM " + TABLE_EXAMES;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);


            if(cursor.moveToFirst()){
                do{
                    if(Integer.parseInt(cursor.getString(5)) == idPaciente &&
                            cursor.getString(7).equals(String.valueOf(0))){
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        Taxas aux = new Taxas();
                        aux.setId(Integer.valueOf(cursor.getString(0)));
                        aux.setGlicose75g(Double.parseDouble(cursor.getString(1)));
                        aux.setGlicoseJejum(Double.parseDouble(cursor.getString(2)));
                        aux.setColesterol(Double.parseDouble(cursor.getString(3)));
                        aux.setDataExame(cursor.getString(4));
                        aux.setIdPac(Integer.valueOf(cursor.getString(5)));
                        aux.setHemoglobinaGlico(Double.parseDouble(cursor.getString(6)));
                        aux.setFlagTaxa(Integer.parseInt(cursor.getString(7)));
                        exames.add(aux);
                    }
                } while(cursor.moveToNext());
            }
        return exames;
    }

    public boolean eraseLastInfoTaxas(Taxas exame){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        String where = this.KEY_ID_EXAME + "=" + String.valueOf(exame.getId());

        values = new ContentValues();
        values.put(KEY_FLAGTAXA, 1);

        return db.update(this.TABLE_EXAMES, values, where, null) > 0;
    }
*/
/*
    public boolean eraseLastInfoPeso(PesoClass peso){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        Log.d("Id Peso Atual : ", String.valueOf(peso.getIdPeso()));
        String where = this.KEY_ID_PESO + "=" + String.valueOf(peso.getIdPeso());

        values = new ContentValues();
        values.put(KEY_FLAGPESO, 1);

        return db.update(this.TABLE_PESOS, values, where, null) > 0;
    }
*/
    public ArrayList<Float> modelGetAllCircunferencias(Paciente paciente){
        int idPaciente = paciente.getId();
        ArrayList<Float> circunferencias = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PESOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {

            do{
                if(Integer.parseInt(cursor.getString(5)) == idPaciente){
                    Log.d("circunferencias: ", cursor.getString(3));
                    circunferencias.add(Float.valueOf(cursor.getString(3)));
                }
            } while(cursor.moveToNext());
        }
        return circunferencias;
    }
/*
    public ArrayList<Float> modelGetGlicosesJejum(Paciente paciente){
        ArrayList<Float> glicosesJejum = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                if(Integer.parseInt(cursor.getString(5)) == paciente.getId()){
                    glicosesJejum.add(Float.valueOf(cursor.getString(2)));
                }
            } while(cursor.moveToNext());
        }
        return glicosesJejum;
    }

    public ArrayList<Float> modelGetGlicoses75g(Paciente paciente){
        ArrayList<Float> glicoses75g = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                if(Integer.parseInt(cursor.getString(5)) == paciente.getId()){
                    glicoses75g.add(Float.valueOf(cursor.getString(1)));
                }
            } while(cursor.moveToNext());
        }
        return glicoses75g;
    }

    public ArrayList<Float> modelGetHemoglobinas(Paciente paciente){
        ArrayList<Float> hemoglobinasGlico = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                if(Integer.parseInt(cursor.getString(5)) == paciente.getId()){
                    hemoglobinasGlico.add(Float.valueOf(cursor.getString(6)));
                }
            } while(cursor.moveToNext());
        }
        return hemoglobinasGlico;
    }

    //metodo chamado na classe Taxas para atualizar medicoes do paciente no banco
    @SuppressLint("LongLogTag")
    public void modelAtualizarTaxas(Paciente paciente){

        //pega data atual
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        Log.d("Data : ", dateString);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;

        //guarda dados do paciente (peso e data atual)
        values = new ContentValues();
        values.put(KEY_GLICOSE75G, paciente.getGlicose75g());
        values.put(KEY_GLICOSEJEJUM, paciente.getGlicoseJejum());
        values.put(KEY_PAC2, paciente.getId());
        values.put(KEY_HEMOGLOBINAGLICO, paciente.getHemoglobinaGlicolisada());
        values.put(KEY_COLESTEROL, paciente.getColesterol());
        values.put(KEY_DATA_EXAME, dateString);

        //insere dados no banco de pesos
        long retorno;
        retorno = db.insert(TABLE_EXAMES, null, values);
        db.close();

        if(retorno == -1){
            Log.d("Erro ao atualizar taxas!", "DatabaseHandler");
        } else {
            Log.d("Taxas atualizadas!", "DatabaseHandler");
        }
    }

    //metodo chamado na classe Taxas para pegar ultima medicao de cada taxa
    public Paciente modelGetUltimasTaxas (Paciente paciente){

        String selectQuery = "SELECT * FROM " + TABLE_EXAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        //procura o peso pelo id do paciente
        Log.d("Pegando ultimas taxas", " <<");
        if(cursor.moveToFirst()){
            do{
                Log.d("Pegou ultimas", " Taxas!");
                if(cursor.getString(5).equals(String.valueOf(paciente.getId()))) {
                    if(Double.parseDouble(cursor.getString(1)) != 0.0) {
                        paciente.setGlicose75g(Double.parseDouble(cursor.getString(1)));
                        Log.d("Glicose75 : ", String.valueOf(paciente.getGlicose75g()));
                    }

                    if(Double.parseDouble(cursor.getString(2)) != 0.0) {
                        paciente.setGlicoseJejum(Double.parseDouble(cursor.getString(2)));
                        Log.d("Glicose Jejum : ", String.valueOf(paciente.getGlicoseJejum()));
                    }

                    if(Double.parseDouble(cursor.getString(3)) != 0.0) {
                        paciente.setColesterol(Double.parseDouble(cursor.getString(3)));
                        Log.d("Colesterol : ", String.valueOf(paciente.getColesterol()));
                    }

                    if(cursor.getString(6)!=null) {
                        if(Double.parseDouble(cursor.getString(6)) != 0.0){
                            paciente.setHemoglobinaGlicolisada(Double.parseDouble(cursor.getString(6)));
                            Log.d("Hemoglobina Glico: ", String.valueOf(paciente.getHemoglobinaGlicolisada()));
                        }
                    }
                }
            } while(cursor.moveToNext());
        }

        Log.d("G75 : ", String.valueOf(paciente.getGlicose75g()));
        Log.d("GJejum : ", String.valueOf(paciente.getGlicoseJejum()));
        Log.d("HemoglobinaGlico : ", String.valueOf(paciente.getHemoglobinaGlicolisada()));

        //retorna paciente com ultimas taxas cadastradas pelo usuario
        return paciente;
    }
*/
}