# Generic Db Loader
A small generic solution (<12 classes) to load data from a persistent storage database and update it in a bunch defined by limit.

I've developed several solutions in java for this purpose in java but this is the first generic one without special modelKeys or heavy use of reflection using groovy. 

It uses the groovy string accessor methods to compare and update the fieldKeys (very nice). This first version uses SpringBoot/JPA, but I will work on next month adding different accessor methods on an object approach (Hibernate/SpringTemplates).

Since the environment is defined by the application, where the loader is merged in, it makes no sense to define something around beside some tests.

Typical application areas could be db synchronization (e.g. prod->test) or merging data from different sources.

One first fill a listBean of object with the method 'addEntry'. When a certain size is reached, the addEntry will return true.

Then one can start the run and reset methods. Depending on the class, it will just load the entries from the db (ListLoaderJPA) or save the entries to the db (ListSaverJPA).

In some previous applications this method with compare is rather fast, synchronizing more than 200 thousands objects in a timeframe of less than 30 seconds and 20 fieldKeys to synchronize.

Example part from the ListSaverJPATest

        ListSaverJPA saver= new ListSaverJPA(repository);
        saver.setSimulated(false);
        saver.setLimit(10);
        saver.setCompareFields(Arrays.asList(new String[]{"testString"}));
        int overallSize=0;

        for (int i=0;i<100;i++) {
            TestObject object=new TestObject(new Long(i),"Test " + i);
            if (saver.addEntry(object)) {  // bunch parts to 10
                saver.run();
                overallSize += saver.size();
                saver.reset();
            }
        }
        // finally
        saver.run();
        overallSize += saver.size();
        log.info("Saved to db with size " + overallSize);
        saver.reset();