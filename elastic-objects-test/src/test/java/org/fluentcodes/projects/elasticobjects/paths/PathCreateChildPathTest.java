package org.fluentcodes.projects.elasticobjects.paths;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.Path;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class PathCreateChildPathTest {

    @Test
    public void given3Elements_then2Elements() {
        Path path = new Path(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Path childPath = path.createChildPath();
        Assertions.assertThat(childPath.directory()).isEqualTo(Path.ofs(S_LEVEL1, S_LEVEL2)) ;
    }
    @Test
    public void given1Element_then0Elements() {
        Path path = new Path(S_LEVEL0);
        Path childPath = path.createChildPath();
        Assertions.assertThat(childPath.directory()).isEqualTo("") ;
    }
    @Test
    public void given1ElementAbsolute_then0Elements() {
        Path path = new Path(Path.DELIMITER, S_LEVEL0);
        Path childPath = path.createChildPath();
        Assertions.assertThat(childPath.directory()).isEqualTo(Path.DELIMITER) ;
    }

}