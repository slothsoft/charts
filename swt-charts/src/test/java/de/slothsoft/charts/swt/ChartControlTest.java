package de.slothsoft.charts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ChartControlTest {

	@Rule
	public SwtRule swtRule = new SwtRule();

	private Shell shell;
	private ChartControl control;

	@Before
	public void setUp() {
		this.shell = new Shell();
		this.shell.setLayout(new FillLayout());
		this.control = new ChartControl(this.shell, SWT.NONE);
	}

	@Test
	public void testName() throws Exception {
		this.shell.open();
		this.shell.dispose();
		// Travis should ignore this test now
	}
}
