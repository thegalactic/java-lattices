package org.thegalactic.descriptionset;

import java.util.TreeSet;
import java.util.Vector;

public class TestDescriptionSet {

	public static void main(String[] args) {
		
		
//		TreeSet<ComparableDescriptionSet<AttributeDescription>> v = new TreeSet<>();
//		
//		AttributeDescription aa = new AttributeDescription("attribute");
//		aa.init("a b");
//		
//		AttributeDescription bb = new AttributeDescription("attribute");
//		bb.init("b");
//		
//		AttributeDescription cc = new AttributeDescription("attribute");
//		cc.init("b c d");
//		
//		AttributeDescription dd = new AttributeDescription("attribute");
//		dd.init("a b c");
//		
//		ComparableDescriptionSet<AttributeDescription> a = ComparableDescriptionSet.create("attribute");
//		a.add(aa);
//		
//		ComparableDescriptionSet<AttributeDescription> b = ComparableDescriptionSet.create("attribute");
//		b.add(bb);
//		
//		ComparableDescriptionSet<AttributeDescription> c = ComparableDescriptionSet.create("attribute");
//		c.add(cc);
//		
//		ComparableDescriptionSet<AttributeDescription> d = ComparableDescriptionSet.create("attribute");
//		d.add(dd);
//		
//		System.out.println(c);
//		System.out.println(d);
//		
//		System.out.println(c.getSimilarity(d));
//		
//		v.add(a);
//		
//		System.out.println(v);
//		
//		v.add(c);
//		
//		System.out.println(v);
//		
//		//v.add(c.getSimilarity(d));
//		
//		//System.out.println(v);
//
//		//System.out.println(c.isEquals(d));
//		System.out.println(c.equals(d));
//		System.out.println(cc.equals(dd));
	
		
		
		
		
		
		try {
			
			// CREATING DESCRIPTION SET CONTEXT
			
			DescriptionSetContext<AttributeDescription> context = new DescriptionSetContext<>("data/pc.txt");
			
			// PRINTING OBSERVATIONS
			
			context.printObservations();
			
			// PRINTING INITIAL DESCRIPTION SETS
			
			context.printInitialDescriptionSets();
			
			// PRINTING ALL DESCRIPTION SETS
			
			context.printAllDescriptionSets();
			
			// PRINTING SPECIALISED INTENT
			for(String o : context.getObservations()){
				System.out.println("Int("+o+") = "+context.getSpecIntent(o));
			}
			
			// PRINTING INTENT
			for(String o : context.getObservations()){
				System.out.println("Int("+o+") = "+context.getIntent(o));
			}
			
			// PRINTING EXTENT
			for(ComparableDescriptionSet ds : context.getAllDescriptionSets()){
				System.out.println("Ext("+ds+") = "+ context.getExtent(ds));
			}
			
			System.out.println("---------");
			System.out.println("--------- ALL CLOSURES");
			System.out.println("---------");

			System.out.println(context.allDescriptionClosures());
		}	
		
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
