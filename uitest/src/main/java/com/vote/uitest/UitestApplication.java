package com.vote.uitest;

public class UitestApplication {

	public static void main(String[] args) {
		// WebDriver driver = new ChromeDriver();
		// try {
		// driver.get("http://localhost:8080");

		// Map<String, String> loginData = new HashMap<>();
		// loginData.put("username", "huang");
		// loginData.put("password", "123321");

		// Login loginPage = new Login(driver, loginData);
		// loginPage.execute().fill().clickSignInButton();

		// VoteIndex votePage = new VoteIndex(driver);
		// votePage.execute().checkBadge().fill().submit().checkResult();

		// Logout logoutPage = new Logout(driver);
		// votePage.execute().submit();

		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// driver.quit();
		// }
		LoginParams loginParams = new LoginParams();
		loginParams.setUsername("huang");
		loginParams.setPassword("123321");
		LoginFlow loginFlow = new LoginFlow(loginParams);
		loginFlow.execute();

		VoteFlow voteFlow = new VoteFlow();
		voteFlow.withStartPage(loginFlow.getEndPage()).execute();

		LogoutFlow logoutFlow = new LogoutFlow();
		logoutFlow.withStartPage(voteFlow.getEndPage()).execute();

	}

}
