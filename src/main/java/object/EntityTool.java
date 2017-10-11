/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package object;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

interface EntityToolFieldInterface{
    String parseField(Field field);
}

interface EntityToolMethodInterface{
    String parseMethod(Method method);
}

/**
 * 基础信息
 *
 * @author liuzh
 * @since 2016-01-31 21:42
 */
public class EntityTool {

    public static String yt_getTableName(Class classz){

        if(classz.isAnnotationPresent(Table.class)){

            Annotation[] annotations = classz.getDeclaredAnnotations();

            for (Annotation annotation :annotations){

                if (annotation.annotationType().equals(Table.class)){

                    Table table = ((Table)annotation);

                    return table.name();

                }

            }

        }

        return null;

    }

    /**
     * 获取字段的指定注解
     * @param field
     * @param classz
     * @param <T>
     * @return
     */
    private static <T> T getFieldName(Field field,Class<T> classz){

        Annotation[] annotations = field.getDeclaredAnnotations();

        return getAnnotation(annotations,classz);

    }

    /**
     * 获取方法的指定注解
     * @param method
     * @param classz
     * @param <T>
     * @return
     */
    private static <T> T getMethodName(Method method,Class<T> classz){
        Annotation[] annotations = method.getDeclaredAnnotations();

        return getAnnotation(annotations,classz);
    }

    private static <T> T getAnnotation(Annotation[] annotations,Class<T> classz){

        for (Annotation annotation : annotations ) {

            if (annotation.annotationType().equals(classz)){

                return (T)annotation;

            }

        }

        return null;
    }


    /**
     * 获取自定model的指定注解的字段 如果没有窜入注解则是获取全部
     * @param entityToolInterface
     * @param classzs
     * @return
     */
    private static String getFieldName(EntityToolFieldInterface entityToolInterface,Class... classzs){

        StringBuffer stringBuffer = new StringBuffer();

        for (Class classz :classzs) {

            Field[] fields =  classz.getDeclaredFields();

            for (Field field : fields) {
                String fieldString = entityToolInterface.parseField(field);
                if (fieldString != null){
                    stringBuffer.append(fieldString).append(",");
                }
            }

        }

        if (stringBuffer.length() > 0 ) stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());

        return stringBuffer.toString();

    }

    /**
     * 获取表列名
     * @param classz
     * @param propertyName
     * @return
     */
    public static String getTableColumnName(Class classz,String propertyName){

        Field[] fields =  classz.getDeclaredFields();

        for (Field field : fields) {
            if (propertyName.toUpperCase().equals(field.getName().toUpperCase())){
                Column column = getFieldName(field,Column.class);
                return StringUtils.isNotEmpty(column.name()) ? column.name():field.getName();
            }
        }

        return null;

    }

    /**
     * 获取指定的方法名
     * @param entityToolMethodInterface
     * @param classzs
     * @return
     */
    private static String getMethodsName(EntityToolMethodInterface entityToolMethodInterface,Class... classzs){

        StringBuffer stringBuffer = new StringBuffer();

        for (Class classz :classzs) {

            Method[] methods = classz.getMethods();

            for(Method method : methods){
                String fieldString = entityToolMethodInterface.parseMethod(method);
                if (fieldString != null){
                    stringBuffer.append(fieldString).append(",");
                }
            }
        }

        if (stringBuffer.length() > 0 ) stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());

        return stringBuffer.toString();

    }


    /**
     * 获取需要模糊查询字段
     * @param classzs
     * @return
     */
    public static String yt_getLikeFieldName(Class... classzs){

        String field = getFieldName(new EntityToolFieldInterface() {
            @Override
            public String parseField(Field field) {
                Like likeColumn = getFieldName(field,Like.class);
                Column column = getFieldName(field,Column.class);
                return likeColumn != null ?  column != null ? column.name():field.getName(): null;
            }
        },classzs);

        return String.format("CONCAT(%s)",field);

    }

    /**
     * 获取统计数字段
     * @param classzs
     * @return
     */
    public static String yt_getCountFieldName(Class... classzs){

        String field = getFieldName(new EntityToolFieldInterface() {
            @Override
            public String parseField(Field field) {
                Count column = getFieldName(field,Count.class);
                return column != null ? field.getName(): null;
            }
        }, classzs);

        return String.format("COUNT(%s)",field);

    }

    /**
     * 获取全部字段
     * @param classzs
     * @return
     */
    public static String yt_getSelectFieldName(Class... classzs){

        return getFieldName(new EntityToolFieldInterface() {
            @Override
            public String parseField(Field field) {
                Column column = getFieldName(field,Column.class);

                if (column != null){
                    return column.name();
                }else {
                    return field.getName();
                }
            }
        }, classzs);

    }

    /**
     * 获取删除标记的字段
     * @param classzs
     * @return
     */
    public static String yt_getDeleteFieldName(Class classzs){

        return getFieldName(new EntityToolFieldInterface() {
            @Override
            public String parseField(Field field) {
                DeleteField deleteColumn = getFieldName(field,DeleteField.class);

                if (deleteColumn != null){
                    Column column = getFieldName(field,Column.class);
                    return StringUtils.isNotEmpty(column.name()) ? column.name() : field.getName();
                }
                return null;
            }
        }, classzs);

    }


    /**
     * 通过get方法获取字段名
     * @param classzs
     * @return
     */
    public static String yt_getSelectFieldByMethods(Class... classzs){

        return getMethodsName((method)->{

            Column column = getMethodName(method,Column.class);

            String methodName = method.getName().toLowerCase();

            if (column != null && methodName.indexOf("get") >= 0){
                return column.name();
            }else{
                return null;
            }
        },classzs);

    }

}
