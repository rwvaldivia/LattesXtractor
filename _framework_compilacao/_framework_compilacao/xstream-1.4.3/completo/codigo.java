   XStream xStream = new XStream(new DomDriver());
			 	 xStream.alias("topicMap", TopicMap.class);
			 	 
			     xStream.alias("topic", Topic.class);
			     xStream.useAttributeFor(Topic.class,"id");
			     
			 	 xStream.alias("association", Association.class);			 	 
			 	 xStream.useAttributeFor(Association.class,"id");
			 	 
			 	 xStream.alias("member", Member.class);
			 	 
			 	 xStream.alias("instanceOf", InstanceOf.class);
			 	 xStream.useAttributeFor(TopicRef.class,"href");
			 	 xStream.aliasAttribute(TopicRef.class,"href","xlink:href");
			 	 xStream.useAttributeFor(SubjectIndicatorRef.class,"href");
			 	 xStream.aliasAttribute(SubjectIndicatorRef.class,"href","xlink:href");
			 			 	
			 	xStream.addImplicitCollection(TopicMap.class, "entries");
			 	xStream.addImplicitCollection(Association.class, "associations");
			 	
			
			 	BufferedReader input = null;
				try {
				//    input = new BufferedReader(new FileReader("C://testeXML.xml"));
					input = new BufferedReader(new FileReader("C://novo.xml"));
				//  input = new BufferedReader(new FileReader("C://testepen.xml"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     
			 	TopicMap topicMap = (TopicMap) xStream.fromXML(input);
			 	 try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	 	