[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![CodeInspector Score](https://www.code-inspector.com/project/5144/score/svg)](https://www.code-inspector.com/project/5144/score)
[![CodeInspector Status](https://www.code-inspector.com/project/5144/status/svg)](https://www.code-inspector.com/project/5144/status)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=KinreeveNaku_CobolIO&metric=bugs)](https://sonarcloud.io/dashboard?id=KinreeveNaku_CobolIO)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=KinreeveNaku_CobolIO&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=KinreeveNaku_CobolIO)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=KinreeveNaku_CobolIO&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=KinreeveNaku_CobolIO)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=KinreeveNaku_CobolIO&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=KinreeveNaku_CobolIO)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=KinreeveNaku_CobolIO&metric=security_rating)](https://sonarcloud.io/dashboard?id=KinreeveNaku_CobolIO)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=KinreeveNaku_CobolIO&metric=coverage)](https://sonarcloud.io/dashboard?id=KinreeveNaku_CobolIO)

----------
COBOLIO
---------

COBOLIO is a library used for parsing and reading mainframe datasets to and from the mainframe. The idea for this project came about due to a request for a parsing library that would incorporate extreme flexibility in how the fields might be handled. A lack of flexibility was encountered when considering Cobol2J, but the core premise of it was very promising.

Bruce Martin's JRecord is also a fantastic library that received much consideration, but, again, the flexibility for type handling configuration was lacking. An idea came to mind of taking the approach that Jackson and BeanIO make use of, and simply create an extensible wrapper for the language.

Will contain an expression language for configuring field patterns which should be handled in their own way, such as joining, or custom reformatting. For example, two fields at the same level where the first is a sign character and the second is a numeric field. The expressions could be configured to always join this pattern into a signed numeric. The primary mechanism for which this will be incorporated will likely be XSLT. This is far from ideal, but it allows the user to be extremely flexible in their design choices. At the same time, XSLT may also make this extremely error-prone, so a 2.0 design may do away with this approach for something better.

This may be used also for learning purposes. I've spent several years working with COBOL's low level logic, particularly on IBM systems, so I'd be happy to hear if I made someone else's learning experience a little bit easier.

Developer Notes
------
It was strongly recommended that use class generation as the templating functionality for translating the copybook xml definitions to java containers, so the decision was made to use Apache Velocity at the core of the project

Perhaps using maps as the initial storage mechanism for the individual fields and then processing some list of instructions that details which fields to handle and how, and then compile the class while translating these reconfigurations. This seems possible but the handling would be immense.

Instructions
------
...

Dependencies and Design
------
* MFDS2J uses Cb2Xml as a direct dependency. It is stored internally due to lack of presence on the central Maven repository. It will be updated to the most recent version whenever they are made available. This also takes aspects of Cobol2J and BeanIO.

* Additionally, this application makes use of design traits that I have learned while working with IBM RecordGen, so additional characteristics in this library may be found in IBM RecordGen.


References
------
* [The IBM eServer z990 floating-point unit](https://www.researchgate.net/profile/Michael_Kroener2/publication/224103047_The_IBM_eServer_z990_floating-point_unit/links/5411bc970cf29e4a23298013/The-IBM-eServer-z990-floating-point-unit.pdf)
* [Examples: numeric data and internal representation](https://www.ibm.com/support/knowledgecenter/SS6SG3_4.2.0/com.ibm.entcobol.doc_4.2/PGandLR/concepts/cpari20e.htm)
* [Coding interoperable datatypes in COBOL and Java](https://www.ibm.com/support/knowledgecenter/SS6SG3_4.2.0/com.ibm.entcobol.doc_4.2/PGandLR/tasks/tpjav03.htm)

License
------
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT)

