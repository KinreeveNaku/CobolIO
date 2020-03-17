<!DOCTYPE html>
<html>
<head>
<style>
div.indented {
	margin-left: 10%;
	margin-right: 10%;
}
</style>
<meta charset="UTF-8">
<title>Mainframe Dataset to Java</title>
</head>
<body>
	<section>
		<div class="indented">
			<h1 style="align-content: center;">MFDS2J</h1>
			<h3 style="align-content: center;">Developed by Kinreeve_Naku</h3>
		</div>
		<div>
			<div class="indented">
				<p>MFDS2J is a library used for parsing and reading mainframe
					datasets off of the mainframe. The idea for this project came about
					due to a request for parsing library that would incorporate extreme
					flexibility in how the fields might be handled. A lack of
					flexibility was encountered when considering Cobol2J, but the core
					premise of it was very promising.</p>
				<p>Bruce Martin's JRecord is also a fantastic library that
					received much consideration, but, again, the flexibility for type
					handling configuration was lacking. An idea came to mind of taking
					the approach that Jackson and BeanIO make use of, and simply create
					an extensible wrapper for the language.</p>
				<p>Will contain an expression language for configuring field patterns which
					should be handled in theirown way, such as joining, or custom
					reformatting.
					For example, two fields at the same level where the first is a sign
					character and the second is a numeric field. The configurations could
					be configured to always join this pattern into a signed numeric.
			</div>
	
		</div>

		<div style="width: 20%; margin-top: -2%;">
			<h3>Developer Notes</h3>
			<img src="./src/main/resources/img/Clorifex.png" longdesc="" alt="" />
			<br /> <sub>Clorifex strongly recommended using class
				generation as the templating functionality for translating the
				copybook xml definitions to java containers, so the decision was
				made to use Apache Velocity at the core of the project</sub>
		</div>
		<div>
			<div>
				<h2>Instructions</h2>
			</div>
			<div>
				<h2>Dependencies and Design</h2>
				<p>MFDS2J uses Cb2Xml as a direct dependency. It is stored
					internally due to lack of presence on the central Maven repository.
					It will be updated to the most recent version whenever they are made
					available. This also takes aspects of Cobol2J and BeanIO.</p>
			</div>
		</div>
	</section>
	<br></br>
	<section>
		<pre>
			
			Copyright 2020 the original author or authors.
			
			Licensed under the Apache License, Version 2.0 (the "License");
			you may not use this file except in compliance with the License.
			You may obtain a copy of the License at
			
			     https://www.apache.org/licenses/LICENSE-2.0
			
			Unless required by applicable law or agreed to in writing, software
			distributed under the License is distributed on an "AS IS" BASIS,
			WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
			See the License for the specific language governing permissions and
			limitations under the License.
			
		</pre>
	</section>
</body>
</html>