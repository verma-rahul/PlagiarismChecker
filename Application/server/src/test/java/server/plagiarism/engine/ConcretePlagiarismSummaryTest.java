package server.plagiarism.engine;

import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.File;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test cases for ConcretePlagiarismSummary
 *
* @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class ConcretePlagiarismSummaryTest {
	// test for instance
	@Test
	public void instanceTest(){
		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		ConcretePlagiarismSummary summary1 = ConcretePlagiarismSummary.instance();
		assertEquals(summary, summary1);
	}

	// test if projects are null
	@Test
	public void generateReportNullProjects(){
		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(null, null);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if null project exists
	@Test
	public void generateReportOneNullProject(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 =  EntityFactory.getInstance().makeProject();;
		p1.setFiles(list1);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, null);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if null project exists
	@Test
	public void generateReportOneNullProject1(){
		File f1 =  EntityFactory.getInstance().makeFile();;
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 =  EntityFactory.getInstance().makeProject();;
		p1.setFiles(list1);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(null, p1);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if there are empty projects
	@Test
	public void generateReportEmptyProjects(){
		Project p1 = EntityFactory.getInstance().makeProject();
		Project p2 = EntityFactory.getInstance().makeProject();

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if there exists empty project
	@Test
	public void generateReportOneEmptyProject(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);

		Project p2 = EntityFactory.getInstance().makeProject();

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if there exists empty project
	@Test
	public void generateReportOneEmptyProject1(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);

		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);
		Project p2 = EntityFactory.getInstance().makeProject();

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p2, p1);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if there exists 2 projects with no files
	@Test
	public void generateReportTwoProjectZeroFileLength(){
		Project p1 =EntityFactory.getInstance().makeProject();
		p1.setFiles(new ArrayList<File>());
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(new ArrayList<File>());

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if there exists project with no files
	@Test
	public void generateReportOneProjectZeroFileLength(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);

		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(new ArrayList<File>());

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if there exists project with no files
	@Test
	public void generateReportOneProjectZeroFileLength1(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(new ArrayList<File>());

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p2, p1);
		Report report = summary.getReport();

		assertEquals(report.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if two projects are the same
	@Test
	public void generateReportSameProjects(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(list1);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		assertEquals(report.getSimilarityScore(), 1.0f, 0.0002);
	}

	// test if the file in 2 projects are not exactly same, with modify of all variable names
	@Test
	public void generateReportDifferentProjects(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("Variable");
		f1.setContent("package astNode;\n" +
				"public class Variable{ \n" +
				"private String variable; \n" +
				"public Variable(String v){ \n" +
				"variable = v;}}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);

		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("Variable2");
		f2.setContent("package ast;\n" +
				"public class Variable2{ \n" +
				"private String var; \n" +
				"public Variable2(String v1){ \n" +
				"var = v1;}}");
		List<File> list2 = new ArrayList<File>();
		list2.add(f2);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(list2);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		assertEquals(report.getSimilarityScore(), 0.8f, 0.0002);
	}

	// test if the file in 2 projects are not exactly same, with same structure and
	// same name but different orders
	@Test
	public void generateReportDifferentProjects1(){
		File f1 =EntityFactory.getInstance().makeFile();
		f1.setName("Variable");
		f1.setContent("package astNode;\n" +
				"public class Variable{ \n" +
				"private String variable; \n" +
				"public Variable(String v){ \n" +
				"variable = v;}}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 =EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);

		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("Variable2");
		f2.setContent("package Variable;\n" +
				"public class astNode{ \n" +
				"private String v; \n" +
				"public Variable(String variable){ \n" +
				"v = variable;}}");
		List<File> list2 = new ArrayList<File>();
		list2.add(f2);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(list2);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		assertEquals(report.getSimilarityScore(), 1.0f, 0.0002);
	}

	// test if the projects have same multiple files
	@Test
	public void generateReportSameMultiFileProjects() throws IOException{
		String filePath = "src/test/resources/PlagiarismChecker-Data/set01/Sample1/LinkedList.java";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		br.close();

		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("Variable");
		f1.setContent("package astNode;\n" +
				"public class Variable{ \n" +
				"private String variable; \n" +
				"public Variable(String v){ \n" +
				"variable = v;}}");
		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("LinkedList");
		f2.setContent(sb.toString());
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		list1.add(f2);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);

		File f3 = EntityFactory.getInstance().makeFile();
		f3.setName("Variable2");
		f3.setContent("package Variable;\n" +
				"public class astNode{ \n" +
				"private String v; \n" +
				"public Variable(String variable){ \n" +
				"v = variable;}}");
		File f4 = EntityFactory.getInstance().makeFile();
		f4.setName("LinkedList1");
		f4.setContent(sb.toString());
		List<File> list2 = new ArrayList<File>();
		list2.add(f3);
		list2.add(f4);
		Project p2 =EntityFactory.getInstance().makeProject();
		p2.setFiles(list2);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		assertEquals(report.getSimilarityScore(), 1.0f, 0.0002);
	}

	// test if the projects have one same multiple files
	@Test
	public void generateReportOneSameMultiFileProjects() throws IOException{
		String filePath = "src/test/resources/PlagiarismChecker-Data/set01/Sample1/LinkedList.java";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		br.close();

		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("Variable");
		f1.setContent("public class Variable{ \n" +
				"private String variable; \n" +
				"public Variable(String v){ \n" +
				"variable = v;}}");
		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("LinkedList");
		f2.setContent(sb.toString());
		List<File> list1 = new ArrayList<File>();
		list1.add(f2);
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);

		File f3 = EntityFactory.getInstance().makeFile();
		f3.setName("Variable2");
		f3.setContent("package node; public class Variable1{}");
		File f4 = EntityFactory.getInstance().makeFile();
		f4.setName("LinkedList1");
		f4.setContent(sb.toString());
		List<File> list2 = new ArrayList<File>();
		list2.add(f3);
		list2.add(f4);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(list2);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.setSimilarityThreshold(0.7f);
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		assertEquals(report.getSimilarityScore(), 0.5f, 0.0002);
	}

	// test if the report is generated correctly
	@Test
	public void getReportTest(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setFiles(list1);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setFiles(list1);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		assertEquals(report.getProject1(), p1);
		assertEquals(report.getProject2(), p2);
	}

	// test if setSimilarityThreshold works  correctly
	@Test
	public void setSimilarityThresholdTest(){
		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.setSimilarityThreshold(0.5f);
		assertEquals(summary.getSimilarityThreshold(), 0.5f, 0.0002);
		summary.setSimilarityThreshold(0.7f);
	}

	// test if the report summary is generated correctly
	@Test
	public void reportSummaryTest(){
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setName("prj1");
		p1.setFiles(list1);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setName("prj2");
		p2.setFiles(list1);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		String reportSummary = "Project prj1 has 1 files, and project prj2 has 1 files.\n" +
				"Summary: average similarity score for the projects is 100.0%\n" +
				"Threshold to test similar files is 70.0%\n" +
				"--------Details for similar files--------\n" +
				"- f1 in Project prj1 and f1 in Project prj2 have the same MD5.\n";
		assertEquals(report.getResultSummary(), reportSummary);
	}

	// test if the report summary is generated correctly
	@Test
	public void reportSummaryTestOneSameMultiFileProjects() throws IOException{
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("Variable");
		f1.setContent("public class Variable{ \n" +
				"private String variable; \n" +
				"public Variable(String v){ \n" +
				"variable = v;}}");
		List<File> list1 = new ArrayList<File>();
		list1.add(f1);
		Project p1 = EntityFactory.getInstance().makeProject();
		p1.setName("prj1");
		p1.setFiles(list1);

		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("Variable2");
		f2.setContent("package node; public class Variable1{}");
		List<File> list2 = new ArrayList<File>();
		list2.add(f2);
		Project p2 = EntityFactory.getInstance().makeProject();
		p2.setName("prj2");
		p2.setFiles(list2);

		ConcretePlagiarismSummary summary = ConcretePlagiarismSummary.instance();
		summary.setSimilarityThreshold(0.7f);
		summary.generateReport(p1, p2);
		Report report = summary.getReport();
		String reportSummary = "Project prj1 has 1 files, and project prj2 has 1 files.\n" +
				"Summary: average similarity score for the projects is 0.0%\n" +
				"Threshold to test similar files is 70.0%\n" +
				"--------Details for similar files--------\n" +
				"No similar files are detected.\n";
		assertEquals(report.getResultSummary(), reportSummary);
	}
}
