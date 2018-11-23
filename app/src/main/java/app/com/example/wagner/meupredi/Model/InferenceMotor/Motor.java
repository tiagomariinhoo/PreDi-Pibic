package app.com.example.wagner.meupredi.Model.InferenceMotor;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.tuple.Triple;

public class Motor extends Activity {

    static String filepath;
    static final String auxFilepath = "aux.txt";
    static Scanner scan = new Scanner(System.in);
    static boolean concDisplay = false;
    static List<Sentence> sentences = new ArrayList<>();
    static List<Triple<String, String, Double>> atoms = new ArrayList<>();
    static Set<String> conclusions = new HashSet<>();
    static Set<Triple<String, String, Double>> histConclusions = new HashSet<>();
    static String history = "";
    static VariableMap vm;
    static Context context;

    public static void setMap(VariableMap vmm, Context contextt) {
        Log.d("Size Sent : ", String.valueOf(sentences.size()));
        Log.d("Size atoms : ", String.valueOf(atoms.size()));
        Log.d("Size concl : ", String.valueOf(conclusions.size()));
        sentences = new ArrayList<>();
        atoms = new ArrayList<>();
        conclusions = new HashSet<>();
        histConclusions = new HashSet<>();
        vm = vmm;
        context = contextt;
    }

    public void listRules() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("rules")));
        String line = reader.readLine();
        int i = 1;
        System.out.println("Rules: ");
        while(line != null) {
            System.out.println(i+" - "+line);
            line = reader.readLine();
            i++;
        }
        if(i == 1) System.out.println("No rules found!");
        reader.close();
    }

    public static void readDatabase() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("rules")));
        String line = reader.readLine();
        Set<Triple<String, String, Double>> auxAtoms = new HashSet<>();
        while(line != null) {
            if(!line.isEmpty()) {
                String sent[] = line.replace("SE", "").split("ENTAO");
                String conds[] = sent[0].split("OU");
                for(int i = 0; i < conds.length; i++) {
                    Sentence sentence = new Sentence(conds[i], sent[1], vm);
                    sentences.add(sentence);
                    auxAtoms.addAll(sentence.getConditions());
                }
            }
            line = reader.readLine();
        }
        atoms.addAll(auxAtoms);
        reader.close();
    }

    //TOP-DOWN
    public static String askQuestions() {
        //vm.printMap();
        for(int i = 0; i < atoms.size(); i++) {
            Triple<String, String, Double> cond = atoms.get(i);
           // Log.d("Teste new", "NEW");
          //  Log.d("Teste2 Questions: ", cond.getLeft()+" "+ cond.getMiddle()+" "+ cond.getRight());
            //qSystem.out.println("NEW");
            //System.out.println(cond.getLeft()+" "+ cond.getMiddle()+" "+ cond.getRight());
            Log.d("Sent : ", cond.getLeft() + cond.getMiddle() + cond.getRight());
            if(vm.checkTriple(cond)) {
                Log.d("True : ", cond.getLeft() + cond.getMiddle() + cond.getRight());
                //DEBUG System.out.println("TRUE");
                checkTrue(cond);
            }
        }

        if(conclusions.isEmpty()) return "No conclusions could be taken";

        return printConclusion(conclusions);
    }

    public static void removeQuestions(Triple<String, String, Double> cond) {
        for(int i = 0; i < sentences.size(); i++) {
            //if you say A is true, then there's no reason to ask if C is true if C only implies A
            if(sentences.get(i).checkConclusion(cond)) {
                sentences.remove(i);
                atoms.remove(cond);
            }
        }
    }

    public static void checkTrue(Triple<String, String, Double> cond) {
        //removes C => A from sentences, if A was already said to be true
        removeQuestions(cond);
        for(int i = 0; i < sentences.size(); i++) {
            Sentence sent = sentences.get(i);
            List<Triple<String, String, Double>> concl = sent.validateCondition(cond);
            if(concl != null) {
                List<String> aux = new ArrayList<>();

                for(int k = 0; k < concl.size(); k++){
                    if(histConclusions.add(concl.get(k))) aux.add(concl.get(k).getLeft());
                    if(concl.get(k).getLeft().matches("\"(.*)\"")) conclusions.add(concl.get(k).getLeft());
                }/*
                concl.forEach(f -> {
                    if(histConclusions.add(f)) aux.add(f.getLeft());
                    if(f.getLeft().matches("\"(.*)\"")) conclusions.add(f.getLeft());
                });*/

                history += sent.getWholeCondition()+" ~> "+getHistory(aux);

                sentences.remove(sent);
                atoms.removeAll(histConclusions);
                for(int k = 0; k < concl.size(); k++){
                  checkTrue(concl.get(k));
                }
                //concl.forEach(j -> checkTrue(j));
            }
        }
    }

    private static String printConclusion(Collection<String> aux) {
        String sep = "";
        for(String c : aux) {
            if(c.matches("\"(.*)\"")) {
                //System.out.print(sep + c);
                return (sep + c);
                //sep = " E ";
            }
        }
        System.out.println();
        return "";
    }

    private static String getHistory(Collection<String> aux) {
        String result = "";
        String sep = "";
        for(String c : aux) {
            result += sep + c;
            sep = " E ";
        }
        return result+"\n";
    }

    public void printList(List<String> list) {
        for(int k = 0; k < list.size(); k++){
            Log.d("PRINT LIST", list.get(k));
        }
        //list.forEach(a -> System.out.println(a));
    }

    public static void printHistory(){
        Log.d("HISTORY", history);
    }
}
