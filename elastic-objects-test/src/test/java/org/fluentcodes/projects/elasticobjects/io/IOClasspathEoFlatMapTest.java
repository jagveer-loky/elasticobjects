package org.fluentcodes.projects.elasticobjects.io;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.HostBean;
import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS_DEV;

public class IOClasspathEoFlatMapTest {
    @Test
    public void DEV_FieldConfigMapMap() {
        IOClasspathEOFlatMap<Map> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS_DEV, "FieldConfig.json", Map.class);
        Map<String, Map> fields = io.read();
        Assertions.assertThat(fields).isNotNull();
        Assertions.assertThat(fields.get("fileName")).isNotNull();
    }

    @Test
    public void TEST_FieldConfigFieldBeanMap() {
        IOClasspathEOFlatMap<FieldBean> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS, "FieldConfig.json", FieldBean.class);
        Map<String, FieldBean> fieldMap = io.read();
        Assertions.assertThat(fieldMap).isNotNull();
        Assertions.assertThat(fieldMap.get("fileName")).isNotNull();
        Assertions.assertThat(fieldMap.get("fileName").getClass()).isEqualTo(FieldBean.class);
    }

    @Test
    public void DEV_ModelConfigMapMap() {
        IOClasspathEOFlatMap<Map> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS_DEV, "ModelConfig.json", Map.class);
        Map<String, Map> modelMap = io.read();
        Assertions.assertThat(modelMap).isNotNull();
        Assertions.assertThat(modelMap.get("ModelBean")).isNotNull();
    }

    @Test
    public void TEST_HostConfigHostBeanMap() {
        IOClasspathEOFlatMap<HostBean> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS, "HostConfig.json", HostBean.class);
        Map<String, HostBean> hostMap = io.read();
        Assertions.assertThat(hostMap).isNotNull();
        Assertions.assertThat(hostMap.get("localhost")).isNotNull();
        Assertions.assertThat(hostMap.get("localhost").getClass()).isEqualTo(HostBean.class);
    }

    @Test
    public void TEST_FileConfigFileBeanMap() {
        IOClasspathEOFlatMap<FileBean> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS, "FileConfig.json", FileBean.class);
        Map<String, FileBean> fileMap = io.read();
        Assertions.assertThat(fileMap).isNotNull();
        Assertions.assertThat(fileMap.get("target.csv")).isNotNull();
        Assertions.assertThat(fileMap.get("target.csv").getClass()).isEqualTo(FileBean.class);
    }
}
