package hashTables;

import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable<AnyType>
{
   private static final int DEFAULT_TABLE_SIZE = 101;

   private List<AnyType> [] theLists;
   private int currentSize = 0;

   public ChainingHashTable()
   {
      this(DEFAULT_TABLE_SIZE);
   }

   @SuppressWarnings("unchecked")
   public ChainingHashTable( int size )
   {
      theLists = new LinkedList[ nextPrime(size) ];
      
      for (int index = 0; index < theLists.length; index++)
      {
         theLists[index] = new LinkedList<>();
      }
   }
   
   private static int nextPrime(int num)
   {
      while(!isPrime(num))
      {
         num++;
      }
      return num;
   }
   
   private static boolean isPrime(int num)
   {
      if (num < 2) 
      {
         return false;
      }
      
      int limit = (int) Math.sqrt(num);
      
      for (int divide = 2; divide <= limit; divide++)
      {
         if (num % divide == 0)
         {
            return false;
         }
      }
      return true;      
   }
   
   public void makeEmpty()
   {
      for (int index = 0; index < theLists.length; index++)
      {
         theLists[index].clear();
      }
      currentSize = 0;
   }
   
   private int myhash(AnyType x )
   {
      int hashVal = x.hashCode();
      
      hashVal %= theLists.length;
      
      if(hashVal < 0)
      {
         hashVal += theLists.length;
      }
      
      return hashVal;
   }
   
   @SuppressWarnings("unchecked")
   private void rehash( )
   {
      List<AnyType> [] oldLists = theLists;
      
      theLists = new LinkedList[ nextPrime( 2 * theLists.length ) ];
      
      for (int index = 0; index < theLists.length; index++)
      {
         theLists[index] = new LinkedList<>();
      }
      
      for (List<AnyType> list: oldLists)
      {
         for (AnyType item: list)
         {
            insert(item);
         }
      }
      
      
   }

   public void insert(AnyType x )
   {
      int hashVal = myhash(x);
      
      List<AnyType> list = theLists[hashVal];
      
      if (!list.contains(x))
      {
         list.add(x);
         
         if (++currentSize > theLists.length)
         {
            rehash();
         }
      }      
   }

   public void remove( AnyType x )
   {
      int hashVal = myhash(x);
      
      List<AnyType> list = theLists[hashVal];
      
      if (list.contains(x))
      {
         list.remove(x);
         currentSize--;
      }
   }

   public boolean contains(AnyType x )
   {
      for (int index = 0; index < theLists.length; index++)
      {
         if (theLists[index].contains(x))
         {
            return true;
         }
      }
      
      return false;
   }

   public static void main(String[] args)
   {
      System.out.println(nextPrime(21));
   }

}

