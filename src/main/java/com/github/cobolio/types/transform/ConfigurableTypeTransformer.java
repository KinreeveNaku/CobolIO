/**
 * 
 */
package com.github.cobolio.types.transform;

import java.util.Properties;

/**
 * @author Andrew
 *
 */
public interface ConfigurableTypeTransformer extends TypeTransformer {

	/**
	 * @param properties
	 * @return
	 */
	TypeTransformer newInstance(Properties properties);

}
