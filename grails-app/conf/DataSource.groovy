import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsAnnotationConfiguration

dataSource {
    configClass = GrailsAnnotationConfiguration.class
    pooled = false
    driverClassName = "com.mysql.jdbc.Driver"
    //username = "root"
    //password = "root"
    //username = "efootprint"
    //password = "ibLaunch123"

    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
    development {
        dataSource {
            
            //--print GORM queries to console
            //logSql = "true"
            
            
            dbCreate = "update" // one of 'create', 'create-drop','update'
            //url = "jdbc:mysql://localhost/ibLGHGCalc"
            url = "jdbc:mysql://localhost/iblghgcalcdb"
            username = "root"
            password = "root"
            //url = "jdbc:mysql://efootprint.db.7340731.hostedresource.com"
            /*
            dbCreate = "update"
            username = "ibLaunch"
            password = "ibLaunch123"
            url = "jdbc:mysql://iblghgcalc.ccdw5fdwq9ea.us-east-1.rds.amazonaws.com:3306/ibLGHGCalc"
            */
        }
    }
    test {
        dataSource {
            
            dbCreate = "update"
            url = "jdbc:mysql://localhost/ibLGHGCalc"
            username = "root"
            password = "root"
            //url = "jdbc:mysql://efootprint.db.7340731.hostedresource.com"
            /*
            dbCreate = "update"
            username = "iblaunch"
            password = "iblaunch123"
            //url = "jdbc:mysql://iblghgcalc.ccdw5fdwq9ea.us-east-1.rds.amazonaws.com/ibLGHGCalc"
            url = "jdbc:mysql://iblghgcalc.ccdw5fdwq9ea.us-east-1.rds.amazonaws.com:3306/iblghgcalcdb"
            */
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:mysql://www.ibLaunch.com/ibLGHGCalc;shutdown=true"
            //url = "jdbc:mysql://www.ibLaunch.com/ibLGHGCalc"
            //url = "jdbc:mysql://efootprint.db.7340731.hostedresource.com"
            //url = "jdbc:mysql://${System.getProperty("dbHostName", "localhost")}/efootprint"
            //url = "jdbc:mysql://${System.getProperty("dbHostName", "localhost")}/ibLGHGCalc"
            username = "iblaunch"
            password = "iblaunch123"
            //url = "jdbc:mysql://dbmaster/ibLGHGCalc"
            //url = "jdbc:mysql://localhost/ibLGHGCalc"
            //url = "jdbc:mysql://https://rds.us-east-1.amazonaws.com:3306/ibLGHGCalc"
            
            //url = "jdbc:mysql://iblghgcalc.ccdw5fdwq9ea.us-east-1.rds.amazonaws.com:3306/iblghgcalcdb"
            
            //rackspace stuff
            //url = "jdbc:mysql://50-57-115-126.static.cloud-ips.com:3306/iblghgcalcdb"
            url = "jdbc:mysql://localhost/iblghgcalcdb"
            

            //url = "jdbc:mysql://iblghgcalc.ccdw5fdwq9ea.us-east-1.rds.amazonaws.com/ibLGHGCalc"
        }
    }
}
