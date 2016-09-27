package no.mesan.faghelgapp;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGenerator {
    private static final int VERSION_NUMBER = 1;

    /**
     * REMEMBER TO INCREASE VERSION NUMBER WHEN CHANGING THE DATABASE AND CREATE UPDATE SCRIPT
     */
    public static void main(String[] args) throws Exception {
        generateNewDb();
    }

    private static void generateNewDb() throws Exception {
        Schema schema = new Schema(VERSION_NUMBER, "no.mesan.model.db");
        schema.setDefaultJavaPackageDao("no.mesan.model.db.dao");
        schema.enableKeepSectionsByDefault();
        generateNewEntities(schema);
        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "../app/src/main/java/");
    }

    private static void generateNewEntities(Schema schema) {
//        Entity subscriptionEntity = schema.addEntity("Product");
//        subscriptionEntity.implementsInterface("Parcelable");
//        subscriptionEntity.addStringProperty("partNumber").primaryKey().notNull();
//        subscriptionEntity.addStringProperty("gtinNumber").index();
//        subscriptionEntity.addStringProperty("description");
    }
}