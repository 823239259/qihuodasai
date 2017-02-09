<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<jsp:directive.include file="includes/top.jsp" />
  <div id="msg" class="success">
    <h2>
    	<c:url value="${credential.username}" />
    	<spring:message code="screen.success.header" />
    	<a href="<c:url value="/logout" />"><spring:message code="screen.success.logout" /></a>
    </h2>
    <p><spring:message code="screen.success.success" /></p>
    <p><spring:message code="screen.success.security" /></p>
  </div>
<jsp:directive.include file="includes/bottom.jsp" />
