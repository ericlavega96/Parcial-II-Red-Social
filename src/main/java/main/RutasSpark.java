package main;

import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

public class RutasSpark {
    public void iniciarSpark(){
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        cfg.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(cfg);
    }
}
