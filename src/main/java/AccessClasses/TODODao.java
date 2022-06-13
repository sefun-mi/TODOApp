package AccessClasses;
import Entities.TODO;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.HashedMap;

import java.util.List;
import java.util.ArrayList;


public class TODODao {
    public static IterableMap<String,ArrayList<TODO>> TODOMap = new HashedMap<>();


    public static boolean addTODO(String userID,TODO item){
        if (TODOMap.containsKey(userID)){
            if (!TODOMap.get(userID).contains(item)){
                ArrayList<TODO> list = TODOMap.get(userID);
                list.add(item);
                TODOMap.replace(userID,list);
                return true;
            }
            System.out.println("TODO item already exists");
            return false;
        }
        else {
            System.out.println("No TODO items exist for this user");
            /* if no list of items has been created then
             a new slot will be initialised for user on
             the map
             * */
            freshTODO(userID);
            addTODO(userID,item);
        }

        return false;
    }

    public static boolean removeTODO(String userID, String title){
        boolean removeStatus = false;
        if (TODOMap.containsKey(userID)){
            if (searchTODO(title) != null){
                ArrayList<TODO> list = TODOMap.get(userID);
                list.remove(searchTODO(title));
                TODOMap.replace(userID,list);
                return true;
            }
            System.out.println("No such Item in TODO list");
        }
        freshTODO(userID);
        return removeStatus;
    }

    public static void freshTODO(String userID){
        if (!TODOMap.containsKey(userID)){
            TODOMap.put(userID,new ArrayList<TODO>());
        }
    }

    public static boolean updateTODO(String userID, String title, boolean update){
        boolean updateStatus = false;
        if (TODOMap.containsKey(userID)){
            if (searchTODO(title) != null){
                TODO temp = searchTODO(title);
                ArrayList<TODO> list = TODOMap.get(userID);
                list.remove(temp);
                temp.setCompleted(update);
                list.add(temp);
                System.out.println("item updated");
                return true;
            }
            System.out.println("TODO item does not exist");
        }
        return updateStatus;
    }

    public static ArrayList<TODO> getAllTODO(String userID){
        if (TODOMap.containsKey(userID)){
            return TODOMap.get(userID);
        }
        System.out.println("No TODO items for user");
        return null;
    }

    public static ArrayList <TODO> getCompletedTODO(String userID){
        ArrayList<TODO> result = new ArrayList<>();
        if (TODOMap.containsKey(userID)){
            ArrayList<TODO> list = TODOMap.get(userID);
            for (TODO item : list){
                if(item.isCompleted()){
                    result.add(item);
                }
            }

        }
        else{
            System.out.println("No TODO items available for this user");
        }
        return result;
    }

    public static ArrayList<TODO> getActiveTODO(String userID){
        ArrayList<TODO> result = new ArrayList<>();
        if (TODOMap.containsKey(userID)){
            ArrayList<TODO> list = TODOMap.get(userID);
            for (TODO item : list){
                if(!item.isCompleted()){
                    result.add(item);
                }
            }

        }
        else{
            System.out.println("No TODO items available for this user");
        }
        return result;
    }

    public static TODO searchTODO(String title){
        MapIterator<String,ArrayList<TODO>> iterator = TODOMap.mapIterator();
        System.out.println("here");
        while (iterator.hasNext()){
            System.out.println("there");
            String key = iterator.next();
            System.out.println(key);
            ArrayList <TODO> items = iterator.getValue();

            for (TODO todo : items ){
                if (todo.getTitle().equals(title)){
                    System.out.println(todo.getTitle());
                    return todo;
                }
            }
        }
        System.out.println("No such TODO item");
        return null;
    }

    public static TODO findTODO (String userID, String reference){
        TODO result = null;
        if (TODOMap.containsKey(userID)){
            if(searchTODO(reference) != null){
                result = searchTODO(reference);
            }
            else{
                ArrayList<TODO> list = TODOMap.get(userID);
                for (TODO todo : list){
                    if ((todo.getDayCreated() != null) && (todo.getDescription() != null)){
                        if (todo.getDayCreated().equals(reference) || todo.getDescription().equals(reference)){
                            return todo;
                        }
                    }

                }
            }
        }
        else{
            System.out.println("No TODO items for this user");
            return null;
        }
        return result;

    }

}
