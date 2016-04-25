package com.van.halley.core.dbmigrate;

/**
 * 简单的文本匹配
 * @author anxin
 *
 */
public abstract class SimpleTextPatternMatcher {
    public static final SimpleTextPatternMatcher DEFAULT_MATCHER = new AllPassPatternMatcher();
    private String pattern;

    public static SimpleTextPatternMatcher create(String pattern) {
    	SimpleTextPatternMatcher simpleTextPatternMatcher;

        if (pattern.equals("/*")) {
        	simpleTextPatternMatcher = DEFAULT_MATCHER;
        } else if (pattern.startsWith("*")) {
            String suffix = pattern.substring(1);
            simpleTextPatternMatcher = new SuffixPatternMatcher(suffix);
        } else if (pattern.endsWith("*")) {
            String prefix = pattern.substring(0, pattern.length() - 1);
            simpleTextPatternMatcher = new PrefixPatternMatcher(prefix);
        } else {
        	simpleTextPatternMatcher = new EqualsPatternMatcher(pattern);
        }

        simpleTextPatternMatcher.setPattern(pattern);

        return simpleTextPatternMatcher;
    }

    public abstract boolean matches(String text);


    public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	/**
     * 后缀matcher
     * @author anxin
     *
     */
    static class SuffixPatternMatcher extends SimpleTextPatternMatcher {
        private String suffix;

        public SuffixPatternMatcher(String suffix) {
            this.suffix = suffix;
        }

        public boolean matches(String text) {
            return text.endsWith(suffix);
        }
    }

    /**
     * 前缀matcher
     * @author anxin
     *
     */
    static class PrefixPatternMatcher extends SimpleTextPatternMatcher {
        private String prefix;

        public PrefixPatternMatcher(String prefix) {
            this.prefix = prefix;
        }

        public boolean matches(String text) {
            return text.startsWith(prefix);
        }
    }

    /**
     * 仅相等的matcher
     * @author anxin
     *
     */
    static class EqualsPatternMatcher extends SimpleTextPatternMatcher {
        private String pattern;

        public EqualsPatternMatcher(String pattern) {
            this.pattern = pattern;
        }

        public boolean matches(String text) {
            return text.equals(pattern);
        }
    }

    /**
     * 默认的全通过的matcher
     * @author anxin
     *
     */
    static class AllPassPatternMatcher extends SimpleTextPatternMatcher {
        public boolean matches(String text) {
            return true;
        }
    }
}
