package org.yaml.snakeyaml;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class DumperOptionsTest extends TestCase {

    public void testDefaultStyle() {
        DumperOptions options = new DumperOptions();
        Yaml yaml = new Yaml(options);
        assertEquals("abc\n", yaml.dump("abc"));
        // string which looks like integer
        assertEquals("'123'\n", yaml.dump("123"));
        //
        options.setDefaultStyle(DumperOptions.DefaultScalarStyle.DOUBLE_QUOTED);
        yaml = new Yaml(options);
        assertEquals("\"123\"\n", yaml.dump("123"));
        //
        options.setDefaultStyle(DumperOptions.DefaultScalarStyle.SINGLE_QUOTED);
        yaml = new Yaml(options);
        assertEquals("'123'\n", yaml.dump("123"));
        //
        options.setDefaultStyle(DumperOptions.DefaultScalarStyle.PLAIN);
        yaml = new Yaml(options);
        assertEquals("'123'\n", yaml.dump("123"));
        assertEquals("abc\n", yaml.dump("abc"));
        // null check
        try {
            options.setDefaultStyle(null);
            fail("Null must not be accepted.");
        } catch (NullPointerException e) {
            assertEquals("Use DefaultScalarStyle enum.", e.getMessage());
        }
    }

    public void testDefaultFlowStyle() {
        Yaml yaml = new Yaml();
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals("[1, 2, 3]\n", yaml.dump(list));
        //
        DumperOptions options = new DumperOptions();
        options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.DefaultFlowStyle.FLOW);
        yaml = new Yaml(options);
        assertEquals("[1, 2, 3]\n", yaml.dump(list));
        //
        options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.DefaultFlowStyle.BLOCK);
        yaml = new Yaml(options);
        assertEquals("- 1\n- 2\n- 3\n", yaml.dump(list));
        // null check
        try {
            options.setDefaultFlowStyle(null);
            fail("Null must not be accepted.");
        } catch (NullPointerException e) {
            assertEquals("Use DefaultFlowStyle enum.", e.getMessage());
        }
    }

    public void testDefaultFlowStyleNested() {
        Yaml yaml = new Yaml();
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("a", "b");
        map.put("c", list);
        assertEquals("a: b\nc: [1, 2, 3]\n", yaml.dump(map));
        //
        DumperOptions options = new DumperOptions();
        options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.DefaultFlowStyle.FLOW);
        yaml = new Yaml(options);
        assertEquals("{a: b, c: [1, 2, 3]}\n", yaml.dump(map));
        //
        options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.DefaultFlowStyle.BLOCK);
        yaml = new Yaml(options);
        assertEquals("a: b\nc:\n- 1\n- 2\n- 3\n", yaml.dump(map));
    }

    public void testCanonical() {
        Yaml yaml = new Yaml();
        assertEquals("123\n", yaml.dump(123));
        //
        DumperOptions options = new DumperOptions();
        options = new DumperOptions();
        options.setCanonical(true);
        yaml = new Yaml(options);
        assertEquals("---\n!!int \"123\"\n", yaml.dump(123));
        //
        options = new DumperOptions();
        options.setCanonical(false);
        yaml = new Yaml(options);
        assertEquals("123\n", yaml.dump(123));
    }

    public void testIndent() {
        Yaml yaml = new Yaml();
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        DumperOptions options = new DumperOptions();
        options.setCanonical(true);
        yaml = new Yaml(options);
        assertEquals("---\n!!seq [\n  !!int \"1\",\n  !!int \"2\",\n]\n", yaml.dump(list));
        //
        options.setIndent(4);
        yaml = new Yaml(options);
        assertEquals("---\n!!seq [\n    !!int \"1\",\n    !!int \"2\",\n]\n", yaml.dump(list));
    }

    public void testLineBreak() {
        Yaml yaml = new Yaml();
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        DumperOptions options = new DumperOptions();
        options.setCanonical(true);
        yaml = new Yaml(options);
        assertEquals("---\n!!seq [\n  !!int \"1\",\n  !!int \"2\",\n]\n", yaml.dump(list));
        //
        options.setLineBreak(DumperOptions.LineBreak.MS);
        yaml = new Yaml(options);
        String output = yaml.dump(list);
        assertEquals("---\r\n!!seq [\r\n  !!int \"1\",\r\n  !!int \"2\",\r\n]\r\n", output);
        // null check
        try {
            options.setLineBreak(null);
            fail("Null must not be accepted.");
        } catch (NullPointerException e) {
            assertEquals("Specify line break.", e.getMessage());
        }
    }

    public void testExplicitStart() {
        Yaml yaml = new Yaml();
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals("[1, 2, 3]\n", yaml.dump(list));
        //
        DumperOptions options = new DumperOptions();
        options = new DumperOptions();
        options.setExplicitStart(true);
        yaml = new Yaml(options);
        assertEquals("--- [1, 2, 3]\n", yaml.dump(list));
        //
        options.setExplicitEnd(true);
        yaml = new Yaml(options);
        assertEquals("--- [1, 2, 3]\n...\n", yaml.dump(list));
    }

    public void testVersion() {
        Yaml yaml = new Yaml();
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals("[1, 2, 3]\n", yaml.dump(list));
        //
        DumperOptions options = new DumperOptions();
        options = new DumperOptions();
        Integer[] version = { 1, 1 };
        options.setVersion(version);
        yaml = new Yaml(options);
        assertEquals("%YAML 1.1\n--- [1, 2, 3]\n", yaml.dump(list));
        //
        Integer[] version0 = { 1, 0 };
        options.setVersion(version0);
        yaml = new Yaml(options);
        assertEquals("%YAML 1.0\n--- [1, 2, 3]\n", yaml.dump(list));
    }

    public void testTags() {
        Yaml yaml = new Yaml();
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals("[1, 2, 3]\n", yaml.dump(list));
        //
        DumperOptions options = new DumperOptions();
        options = new DumperOptions();
        Map<String, String> tags = new LinkedHashMap<String, String>();
        tags.put("!foo!", "bar");
        options.setTags(tags);
        yaml = new Yaml(options);
        assertEquals("%TAG !foo! bar\n--- [1, 2, 3]\n", yaml.dump(list));
        //
        options = new DumperOptions();
        tags.put("!yaml!", "tag:yaml.org,2002:");
        yaml = new Yaml(options);
        assertEquals("foo\n", yaml.dump("foo"));
    }

    public void testAllowUnicode() {
        Yaml yaml = new Yaml();
        assertEquals("out: " + yaml.dump("\u00DCber"), "\u00DCber\n", yaml.dump("\u00DCber"));
        //
        DumperOptions options = new DumperOptions();
        options = new DumperOptions();
        options.setAllowUnicode(false);
        yaml = new Yaml(options);
        assertEquals("\"\\xdcber\"\n", yaml.dump("\u00DCber"));
    }

    public void testSetRootTag() {
        DumperOptions options = new DumperOptions();
        try {
            options.setExplicitRoot(null);
            fail("Root tag is required.");
        } catch (NullPointerException e) {
            assertEquals("Root tag must be specified.", e.getMessage());
        }
    }

    public void testToString() {
        DumperOptions.DefaultScalarStyle scalarStyle = DumperOptions.DefaultScalarStyle.LITERAL;
        assertEquals("Scalar style: '|'", scalarStyle.toString());
        //
        DumperOptions.DefaultFlowStyle flowStyle = DumperOptions.DefaultFlowStyle.BLOCK;
        assertEquals("Flow style: 'false'", flowStyle.toString());
        //
        DumperOptions.LineBreak lb = DumperOptions.LineBreak.Linux;
        assertEquals("Line break: Linux", lb.toString());
    }
}
