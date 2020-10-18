<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>COBOLIO</title>
</head>
<body>
<div>
	<table>
	<tr>
	<td>
	<a href="https://frontend.code-inspector.com/public/project/5144/CobolIO/dashboard">Code-Inspector</a>
	</td>
	<td>
	<object>
		<img src="https://www.code-inspector.com/project/5144/score/svg"></img>
		<img src="https://www.code-inspector.com/project/5144/status/svg"></img>
	</object>
	</td>
	</tr>
	</table>
	<div class="indented">
		<h1 style="align-content: center;">COBOLIO</h1>
		<h3 style="align-content: center;">Developed by Kinreeve_Naku</h3>
	</div>
	<div class="indented">
		<div>
			<p>COBOLIO is a library used for parsing and reading mainframe
				datasets to and from the mainframe. The idea for this project came about
				due to a request for a parsing library that would incorporate extreme
				flexibility in how the fields might be handled. A lack of
				flexibility was encountered when considering Cobol2J, but the core
				premise of it was very promising.</p>
			<p>Bruce Martin's JRecord is also a fantastic library that
				received much consideration, but, again, the flexibility for type
				handling configuration was lacking. An idea came to mind of taking
				the approach that Jackson and BeanIO make use of, and simply create
				an extensible wrapper for the language.</p>
			<p>Will contain an expression language for configuring field
				patterns which should be handled in their own way, such as joining,
				or custom reformatting. For example, two fields at the same level
				where the first is a sign character and the second is a numeric
				field. The expressions could be configured to always join this
				pattern into a signed numeric. The primary mechanism for which this will
				be incorporated will likely be XSLT. This is far from ideal, but it allows
				the user to be extremely flexible in their design choices. At the same time,
				XSLT may also make this extremely error-prone, so a 2.0 design may do away
				with this approach for something better.
			</p>
		</div>
		<h3>Developer Notes</h3>
		<img src="./src/main/resources/img/Clorifex.PNG" longdesc="" alt="" />
		<br /> <sub>Clorifex strongly recommended using class
			generation as the templating functionality for translating the
			copybook xml definitions to java containers, so the decision was
			made to use Apache Velocity at the core of the project</sub>
	</div>
	<div class="indented">
		<div>
			<h2>Instructions</h2>
			<p>...</p>
		</div>
		<div>
			<h2>Dependencies and Design</h2>
			<p>MFDS2J uses Cb2Xml as a direct dependency. It is stored
				internally due to lack of presence on the central Maven repository.
				It will be updated to the most recent version whenever they are
				made available. This also takes aspects of Cobol2J and BeanIO.
			</p>
			<p>
				Additionally, this application makes use of design traits that
				I have learned while working with IBM RecordGen, so additional
				characteristics in this library may be found in IBM RecordGen.
			</p>
		</div>
	</div>
</div>

------------------------------------------------------------

<div style="margin-left: 10%; margin-right: 10%;">
<div style="align-content: center;">
<p><noscript>Copyright &copy; 2020 the original author or authors.</noscript></p>
</div>
<div style="align-content: center;">
<p>Licensed under the Apache License, Version 2.0 (the "License");<br />
you may not use this library except in compliance with the License.</p>
</div>
<div style="align-content: center;">
You may obtain a copy of the License at
</div>
<div>
<a href="https://www.apache.org/licenses/LICENSE-2.0">https://www.apache.org/licenses/LICENSE-2.0</a>
</div>
<div>
<p>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
</div>
<div>See the License for the specific language governing permissions and
limitations under the License.</p>
</div>
</div>
</body>
</html>
