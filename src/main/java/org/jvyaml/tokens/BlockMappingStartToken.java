/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.jvyaml.tokens;

import org.jvyaml.Mark;

/**
 * @see PyYAML 3.06 for more information
 */
public class BlockMappingStartToken extends Token {

    public BlockMappingStartToken(Mark startMark, Mark endMark) {
        super(startMark, endMark);
    }

    @Override
    public String getTokenId() {
        return "<block mapping start>";
    }
}
