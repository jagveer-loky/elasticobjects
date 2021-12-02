package org.fluentcodes.projects.elasticobjects.paths;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_EMPTY;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;

public class PathTest {

    @Test
    public void constructorStringEmpty() {
        Assert.assertEquals("", new Path(S_EMPTY).directory());
        Assert.assertEquals("", new Path(PathElement.SAME).directory());
        Assert.assertEquals(Path.DELIMITER, new Path(Path.DELIMITER).directory());
        Assert.assertEquals(Path.DELIMITER, new Path("///").directory());
        Assert.assertEquals(Path.DELIMITER, new Path("/././").directory());
        Assert.assertEquals(Path.DELIMITER, new Path("/ /./\t/").directory());
    }

    @Test
    public void constructorString_back() {
        Assert.assertEquals("../../back", new Path("../../back").directory());
    }

    @Test
    public void givenBackAtTheMiddle_thenRemains() {
        String other = Path.ofs(PathElement.SAME, PathElement.BACK, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assertions.assertThat(new Path(other).directory()).isEqualTo(Path.ofs(PathElement.BACK, S_LEVEL0, S_LEVEL1, S_LEVEL2));
    }

    @Test
    public void givenBackInTheBeginning_thenRemains() {
        String toCompare = Path.ofs(PathElement.BACK, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assertions.assertThat(new Path(toCompare).directory()).isEqualTo(toCompare);
    }

    @Test
    public void constructorString_SeveralBacksAtTheBeginning() {
        
        String toCompare = Path.ofs(PathElement.BACK, PathElement.BACK, PathElement.BACK, S_LEVEL0);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
    }

    @Test
    public void givenBackInTheMiddle_thenPrecedingElementIsCut() {
        String toCompare = Path.ofs(S_LEVEL0, S_LEVEL2);
        Assertions.assertThat(new Path(S_LEVEL0, S_LEVEL1, PathElement.BACK, S_LEVEL2).directory()).isEqualTo(toCompare);
    }

    @Test
    public void givenBackAtTheEnd_thenPrecedingElementIsCut() {
        String toCompare = Path.ofs(S_LEVEL0, S_LEVEL1);
        Assertions.assertThat(new Path(S_LEVEL0, S_LEVEL1, S_LEVEL2, PathElement.BACK).directory()).isEqualTo(toCompare);
    }


    @Test
    public void given3Entries_thenSizeIs3() {
        Path path = new Path(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assertions.assertThat(path.size()).isEqualTo(3);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + Path.DELIMITER + S_LEVEL1 + Path.DELIMITER + S_LEVEL2);
    }

    @Test
    public void given3EntriesAsString_thenSizeIs3() {
        Path path = new Path(S_LEVEL0 + Path.DELIMITER + S_LEVEL1 + Path.DELIMITER + S_LEVEL2);
        Assertions.assertThat(path.size()).isEqualTo(3);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + Path.DELIMITER + S_LEVEL1 + Path.DELIMITER + S_LEVEL2);
    }

    @Test
    public void given3EntriesAsStringWithOneEmpty_thenSizeIs2() {
        Path path = new Path(S_LEVEL0 + Path.DELIMITER  + Path.DELIMITER + S_LEVEL2);
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + Path.DELIMITER + S_LEVEL2);
    }

    @Test
    public void givenFirstPathDelimiter_whenIsAbsolute_thenTrue() {
        Path path = new Path(Path.DELIMITER, S_LEVEL0);
        Assertions.assertThat(path.isAbsolute()).isTrue();
        Assertions.assertThat(path.size()).isEqualTo(1);
        Assertions.assertThat(path.directory()).isEqualTo(Path.DELIMITER + S_LEVEL0);
    }

    @Test
    public void given3EntriesWith1Empty_thenSizeIs2() {
        Path path = new Path(S_LEVEL0, "", S_LEVEL2);
        Assertions.assertThat(path.isAbsolute()).isFalse();
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + Path.DELIMITER + S_LEVEL2);
    }

    @Test
    public void given3Entrieswith1Null_thenSizeIs2() {
        Path path = new Path(S_LEVEL0, null, S_LEVEL2);
        Assertions.assertThat(path.isAbsolute()).isFalse();
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + Path.DELIMITER + S_LEVEL2);
    }

    @Test
    public void given3EntriesAnd1Same_thenSizeIs2() {
        Path path = new Path(S_LEVEL0, PathElement.SAME, S_LEVEL2);
        Assertions.assertThat(path.isAbsolute()).isFalse();
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + Path.DELIMITER + S_LEVEL2);
    }


    @Test
    public void hasMatcher() {
        
        Path path = new Path(Path.ofs(S_LEVEL0, PathElement.MATCHER, S_LEVEL1));
        Assert.assertTrue(path.hasMatcher());
        path = new Path(Path.ofs(S_LEVEL0, S_LEVEL1));
        Assert.assertFalse(path.hasMatcher());
        path = new Path(S_EMPTY);
        Assert.assertFalse(path.hasMatcher());
    }

    @Test
    public void hasPlaceHolder() {
        Path path = new Path(Path.ofs(S_LEVEL0, "[test]"));
        Assert.assertTrue(path.hasPlaceHolder());
        path = new Path(Path.ofs(S_LEVEL0));
        Assert.assertFalse(path.hasPlaceHolder());
        path = new Path(S_EMPTY);
        Assert.assertFalse(path.hasPlaceHolder());
    }

    @Test
    public void level0Level1_andAbsoluteLevel2__new__absoluteLevel2() {
        Path path = new Path("level0/level1", "/level2");
        Assertions.assertThat(path.toString()).hasToString("/level2");
    }

}