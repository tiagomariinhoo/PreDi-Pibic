package app.com.example.wagner.meupredi.SecondVersion;

public class LixoDatabaseHandler {
    /** EXERCICIOS
     *
     * String CREATE_EXERCICIOS_TABLE = "CREATE TABLE IF NOT EXISTS "
     + TABLE_EXERCICIOS
     + "("
     + KEY_ID_EXERCICIO + " INTEGER PRIMARY KEY,"
     + KEY_NOME_EXERCICIO + " TEXT,"
     + KEY_TEMPO + " INTEGER,"
     + KEY_DATA_EXERCICIO + " INTEGER,"
     + KEY_PAC3 + " INTEGER,"
     + " FOREIGN KEY ("+KEY_PAC3+") REFERENCES "+TABLE_PACIENTES+"("+KEY_ID+"));";
     db.execSQL(CREATE_EXERCICIOS_TABLE);
     *
     * public String modelAddExercicio(int tempo, String exercicio, Paciente paciente) {
     int idPaciente = paciente.get_id();
     GregorianCalendar calendar = new GregorianCalendar();
     int dia =  calendar.get(GregorianCalendar.DAY_OF_YEAR);

     Log.d("Dia do ano : ", String.valueOf(dia));
     Log.d("Tempo do exercicio : ", String.valueOf(tempo));

     SQLiteDatabase db = this.getWritableDatabase();

     ContentValues values = new ContentValues();

     Log.d("Adicionando : " , "Exercicio");

     values.put(KEY_TEMPO, tempo);
     values.put(KEY_DATA_EXERCICIO, dia);
     values.put(KEY_NOME_EXERCICIO, exercicio);
     values.put(KEY_PAC3, idPaciente);

     long retorno;
     retorno = db.insert(TABLE_EXERCICIOS, null, values);

     if(retorno == -1){
     return "Erro ao inserir o registro do exercício!";
     } else {
     return "Exercicio inserido com sucesso!";
     }
     }

     public ArrayList<ExercicioClass> modelGetAllExercicios (Paciente paciente) throws ParseException {
     ArrayList<ExercicioClass> exList = new ArrayList<>();
     int idPaciente = paciente.get_id();
     Log.d("DB ","entrei");

     String selectQuery = "SELECT * FROM " + TABLE_EXERCICIOS;

     SQLiteDatabase db = this.getReadableDatabase();
     Cursor cursor = db.rawQuery(selectQuery, null);

     if(cursor.moveToFirst()){
     do{
     if(Integer.parseInt(cursor.getString(4))==idPaciente) {
     ExercicioClass exClass = new ExercicioClass();
     exClass.setNome(cursor.getString(1));
     exClass.setTempo(Integer.parseInt(cursor.getString(2)));
     exClass.setData(Integer.parseInt(cursor.getString(3)));
     exClass.setIdPaciente(Integer.parseInt(cursor.getString(4)));
     exList.add(exClass);
     }
     }while(cursor.moveToNext());
     }

     return exList;
     }

     // ---------- TABLE EXERCICIOS ----------
     //TABLE EXERCICIOS
     private static final String TABLE_EXERCICIOS = "exercicios";

     //COLUNAS DOS EXERCICIOS
     private static final String KEY_ID_EXERCICIO = "idExercicio";
     private static final String KEY_TEMPO = "tempo";
     private static final String KEY_NOME_EXERCICIO = "nomeExercicio";
     private static final String KEY_DATA_EXERCICIO = "dataExercicio";
     private static final String KEY_PAC3 = "pac3";

     */

}
