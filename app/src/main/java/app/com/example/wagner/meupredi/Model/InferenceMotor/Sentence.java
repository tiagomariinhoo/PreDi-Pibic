package app.com.example.wagner.meupredi.Model.InferenceMotor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Sentence {
    private String wholeCondition;
    private List<Triple<String, String, Double>> conditions = new ArrayList<>();
    private List<Triple<String, String, Double>> conclusions = new ArrayList<>();
    private VariableMap vm;

    public Sentence(String cond, String concl, VariableMap vm) {
        this.vm = vm;

        cond = cond.replace("(", "").replace(")", "");
        wholeCondition = (cond.trim());
        String aux[] = cond.split("E");
        for(int i = 0; i < aux.length; i++){
            aux[i] = aux[i].trim();
            addCond(aux[i]);
        }
        //aux = aux.stream().map(String::trim).collect(Collectors.toList());
        //aux.forEach(a -> addCond(a));
        //conditions = conditions.stream().map(String::trim).collect(Collectors.toList());
        //conditions.forEach(c -> addToVM(c));
        //conditions = conditions.stream().map(c -> removeOp(c)).collect(Collectors.toList());

        concl = concl.replace("(", "").replace(")", "");
        aux = concl.split("E");
        for(int i = 0; i < aux.length; i++){
            aux[i] = aux[i].trim();
            addConcl(aux[i]);
        }
        //aux = aux.stream().map(String::trim).collect(Collectors.toList());
        //aux.forEach(a -> addConcl(a));
        //conclusions = conclusions.stream().map(String::trim).collect(Collectors.toList());
    }

    public List<Triple<String, String, Double>> validateCondition(Triple<String, String, Double> cond) {
        conditions.remove(cond);
        if(conditions.isEmpty()) {
            for(int i = 0; i < conclusions.size(); i++){
                Log.d("Conclusion : ", conclusions.get(i).getLeft() + conclusions.get(i).getMiddle() + conclusions.get(i).getRight());
                vm.addUserInfo(conclusions.get(i));
            }
            //conclusions.forEach(c -> vm.addUserInfo(c));
            return conclusions;
        }
        else return null;
    }

    public boolean checkConclusion(Triple<String, String, Double> cond) {
        List<Triple<String, String, Double>> aux = new ArrayList<>();

        aux.add(cond);

        if(conclusions.equals(aux)) return true;
        else return false;
    }

    public List<Triple<String, String, Double>> getConclusions() {
        return conclusions;
    }

    public List<Triple<String, String, Double>> getConditions() {
        return conditions;
    }

    public String getWholeCondition() {
        return wholeCondition;
    }

    private void addCond(String cond) {
        Triple<String, String, Double> t = getTriple(cond);
        //System.out.println(t);
        conditions.add(t);
        vm.addVariable(t);
    }

    private void addConcl(String concl) {
        Triple<String, String, Double> t = getTriple(concl);
        conclusions.add(t);
    }

    private Triple<String, String, Double> getTriple(String c) {
        c = c.trim();
        Triple<String, String, Double> t;
        if(c.contains("==")) {
            String[] result = c.split("==");
            t = new ImmutableTriple<String, String, Double>(result[0].trim(), "==", Double.parseDouble(result[1].trim()));
        }
        else if(c.contains(">=")) {
            String[] result = c.split(">=");
            t = new ImmutableTriple<String, String, Double>(result[0].trim(), ">=", Double.parseDouble(result[1].trim()));
        }
        else if(c.contains("<=")) {
            String[] result = c.split("<=");
            t = new ImmutableTriple<String, String, Double>(result[0].trim(), "<=", Double.parseDouble(result[1].trim()));
        }
        else if(c.contains(">")) {
            String[] result = c.split(">");
            t = new ImmutableTriple<String, String, Double>(result[0].trim(), ">", Double.parseDouble(result[1].trim()));
        }
        else if(c.contains("<")) {
            String[] result = c.split("<");
            t = new ImmutableTriple<String, String, Double>(result[0].trim(), "<", Double.parseDouble(result[1].trim()));
        }
        else if(c.contains("!=")) {
            String[] result = c.split("!=");
            t = new ImmutableTriple<String, String, Double>(result[0].trim(), "!=", Double.parseDouble(result[1].trim()));
        }
        else {
            if(c.contains("~")) {
                t = new ImmutableTriple<String, String, Double>(c.substring(1), "!=", 1.0);
            }
            else {
                t = new ImmutableTriple<String, String, Double>(c, "==", 1.0);
            }
        }
        return t;
    }

}