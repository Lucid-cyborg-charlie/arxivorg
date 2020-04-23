package app.arxivorg.model;

import java.io.*;
import java.util.*;


public class User {

    private static final String fileName = "src/main/resources/app/arxivorg/data/favoris.txt";

    /**
     * save the id of article in favoris.txt
     * @param id
     */
    public static boolean saveArticle(String id){
        return writeFile(getLastValue(id));
    }

    /**
     * remove the id of article in favoris.txt
     */
    public static boolean removeArticle(String id){
        String lastValue=getLastValue(id);
        BufferedWriter file = null;
        try {
            List<String> idList=readFile();
            StringBuffer stringBuffer=new StringBuffer();
            file = new BufferedWriter(new FileWriter(""));
            for(String e: idList){
                if(!e.equals(lastValue))
                    stringBuffer.append(e+"\n");
            }
            file.write(stringBuffer.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * remove all articles
     */
    public static void removeAllArticles(){
        List<String> listId=readFile();
        for(String id: listId){
            removeArticle(id);
        }
    }

    /**
     * Read the file favoris.txt
     * @return list of id
     */
    public static List<String> readFile(){
        List<String> idList=new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                idList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }

    /**
     *
     * @param id
     * @return treturns true if id is registered
     */
    private static boolean writeFile(String id){
        BufferedWriter file = null;
        try {
            List<String> idList=readFile();
            StringBuffer stringBuffer=new StringBuffer();
            file = new BufferedWriter(new FileWriter(fileName));
            for(String e: idList){
                stringBuffer.append(e+"\n");
            }
            if(!idList.contains(id)){
                stringBuffer.append(id);
            }
            file.write(stringBuffer.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @param id
     * @return last value of id
     */
    public static String getLastValue(String id){
        String[] tab=id.split("/");
        return tab[tab.length-1];
    }

    /**
     *
     * @return list of article by ID
     */
    public static List<Article> getArticlesByID(){
        List<Article> articles=null;
        List<String> idList=readFile();
        if(idList.contains(""))
            idList.remove("");
        String query="https://export.arxiv.org/api/query?id_list="+getIdList(idList);
        return ManagerArticle.loadDataFromAPI(query);
    }

    /**
     *
     * @param list
     * @return comma-separated list of identifiers
     */
    public static String getIdList(List<String> list){
        StringBuffer stringBuffer=new StringBuffer();
        int i=0;
        for(String id: list){
            if(i < list.size()-1)
                stringBuffer.append(id+",");
            else stringBuffer.append(id);
            i++;
        }
        return stringBuffer.toString();
    }

}
