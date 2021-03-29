package ReflectionTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class ReflectionTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Test test = new Test();


//тут мы "преобразуем" класс Test в объект класса Class
        Class clazz = Test.class;
//тут преобразуем уже объект класса Тест в объект класса Class
        Class clazz1 = test.getClass();
//тут получаем объект класса Class по имени класса (путь)
        Class clazz2 = Class.forName("ReflectionTest.Test");

        //тут получаем список методов
//        Method[] methods = clazz.getMethods();
//        for (Method m: methods) {
//            System.out.println(m.getName() + ", "+ m.getReturnType() +", "+ Arrays.toString(m.getParameterTypes()));
//        }
//
////тут получаем список полей. если поле приватное, то нужно использовать метод getDeclaredFields()
//        Field [] fields = clazz.getDeclaredFields();
//        for (Field f: fields) {
//            System.out.println(f.getName());
//        }

        //что можно еще сделать с пом. рефлексии:

        //создаем два экземпляра Class от Тест
        Class class1 = Class.forName("ReflectionTest.Test");
        Class class2 = Class.forName("java.lang.String");
        //создаем метод, в параметрах указываем название метода и параметр метода (в данном случае юзаем параметр второго класса
        // так как мы знаем, что вторым классном у нас будет String
        Method m = class1.getMethod("setName", class2);

        //создать экземпляр класса с нужным конструктором
        Object obj1 = class1.getConstructor(int.class, String.class).newInstance(10, "me");
        //создаем объект стринга (там есть конструктор, который на вход принимает строку и делает новую строку из входной строки
        Object obj2 = class2.getConstructor(String.class).newInstance("New name");
        //вызыввем метод
        m.invoke(obj1, obj2);
        //видим, что в результате имя поменялось
        System.out.println(obj1);

    }
}
