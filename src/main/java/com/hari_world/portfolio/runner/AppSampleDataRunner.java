package com.hari_world.portfolio.runner;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hari_world.portfolio.entity.Banner;
import com.hari_world.portfolio.entity.LoginCredentials;
import com.hari_world.portfolio.entity.Post;
import com.hari_world.portfolio.entity.Profile;
import com.hari_world.portfolio.entity.Subscriber;
import com.hari_world.portfolio.reposirory.BannerRepository;
import com.hari_world.portfolio.reposirory.PostRepository;
import com.hari_world.portfolio.reposirory.SubscriberRepository;
import com.hari_world.portfolio.service.ILoginCredentialsService;

@Component
public class AppSampleDataRunner implements CommandLineRunner {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private BannerRepository bannerRepository;
	@Autowired
	private ILoginCredentialsService credentialsService;
	@Autowired
	private SubscriberRepository subscriberRepository;

	@Override
	public void run(String... args) throws Exception {
		// Profile Data
		Profile profile = new Profile();
		// profile.setId(1);
		profile.setName("Harikumar Borra");
		profile.setTagline("Trainee Mechanical Engineer | Robotics & Automation");
		profile.setBio(
				"I am a mechanical automation engineer with a strong interest in robotics, industrial automation,\r\n"
						+ "   and product-oriented mechanical design. My professional focus lies in designing reliable,\r\n"
						+ "   manufacturable, and automation-ready mechanical systems that integrate seamlessly with\r\n"
						+ "   robotics, PLC-based control systems, and modern manufacturing environments.\r\n" + "\r\n"
						+ "   I have hands-on exposure to mechanical design using CAD tools, including part modeling,\r\n"
						+ "   assembly design, and basic simulation concepts. My areas of interest include GD&T application,\r\n"
						+ "   DFM/DFA principles, jigs and fixtures design, BIW fixture concepts, and robotic workcell layouts.\r\n"
						+ "   I enjoy understanding how mechanical decisions influence automation performance, accuracy,\r\n"
						+ "   cycle time, and long-term maintainability.\r\n" + "\r\n"
						+ "   I am particularly interested in industrial robots, end-of-arm tooling (EOAT),\r\n"
						+ "   fixture design for robotic welding and assembly lines, and sensor integration\r\n"
						+ "   in automated systems. I continuously work on improving my understanding of how\r\n"
						+ "   mechanical design, automation hardware, and software systems interact in real-world\r\n"
						+ "   production environments.\r\n" + "\r\n"
						+ "   Alongside my mechanical background, I am actively learning how to build\r\n"
						+ "   supporting software systems using Spring Boot and modern web technologies\r\n"
						+ "   to create engineering dashboards, internal tools, and personal technical blogs.\r\n"
						+ "   My long-term goal is to grow into a well-rounded mechanical design and automation\r\n"
						+ "   engineer capable of contributing to end-to-end product and system development.");
		profile.setMobile("+919381815664");
		profile.setEmail("harikumarborra@hari-world.click");
		profile.setLinkedin("https://www.linkedin.com/in/hari-engineer");
		profile.setInstagram("https://www.instagram.com/hari.engineer");
		profile.setTwitter("https://twitter.com/hari_engineer");
		profile.setProfilePicUrl(
				"https://res.cloudinary.com/ddzbdknua/image/upload/v1759064323/Harikumar_Borra_eh5f5a.jpg");

		// profile = profileRepository.save(profile);

		// Posts Data
		Post post1 = new Post();
		post1.setTitle("Introduction to Industrial Robots in Automation");
		post1.setSlug("introduction-to-industrial-robots-in-automation");
		post1.setThumnailUrl(
				"https://cdn.shopify.com/s/files/1/0028/7509/7153/files/iStock-674906266_grande.jpg?v=1590710940");
		post1.setSummary(
				"An overview of industrial robots, their types, applications, and role in modern automated manufacturing systems.");
		post1.setContent(
				"An overview of industrial robots, their types, applications, and role in modern automated manufacturing systems.',\r\n"
						+ "  'Industrial robots are programmable, multi-axis machines used extensively in manufacturing automation.\r\n"
						+ "   They are widely applied in welding, painting, material handling, assembly, and inspection operations.\r\n"
						+ "\r\n"
						+ "   According to the International Federation of Robotics (IFR), industrial robot installations continue\r\n"
						+ "   to grow due to increasing demand for productivity, quality, and workplace safety.\r\n"
						+ "   Common robot configurations include articulated robots, SCARA robots, delta robots, and Cartesian robots.\r\n"
						+ "\r\n"
						+ "   Leading industrial robot manufacturers include KUKA, FANUC, ABB, and YASKAWA.\r\n"
						+ "   These robots are typically integrated with PLCs, vision systems, safety scanners, and conveyors\r\n"
						+ "   to form fully automated production lines.");
		post1.setPublished(true);

		Post post2 = new Post();
		post2.setTitle("PLC-Based Automation Systems Explained");
		post2.setSlug("plc-based-automation-systems-explained");
		post2.setThumnailUrl("https://duplico.com/wp-content/uploads/2023/10/projektiranje-elektro-ormara.jpg");
		post2.setSummary(
				"A practical guide to PLC-based automation systems, architecture, programming standards, and real-world use cases.");
		post2.setContent("Programmable Logic Controllers (PLCs) are the backbone of industrial automation systems.\r\n"
				+ "   They are designed to operate reliably in harsh industrial environments and control machines and processes\r\n"
				+ "   through digital and analog I/O.\r\n" + "\r\n"
				+ "   PLC programming is standardized under IEC 61131-3, which defines languages such as Ladder Logic,\r\n"
				+ "   Function Block Diagram, Structured Text, and Sequential Function Charts.\r\n" + "\r\n"
				+ "   Popular PLC platforms include Siemens SIMATIC S7, Allen-Bradley ControlLogix, Mitsubishi MELSEC,\r\n"
				+ "   and Schneider Electric Modicon.\r\n"
				+ "   PLCs are commonly integrated with HMIs, servo drives, safety relays, and industrial networks\r\n"
				+ "   such as PROFINET, EtherNet/IP, and Modbus.");
		post2.setPublished(true);

		Post post3 = new Post();
		post3.setTitle("Design of Jigs and Fixtures in Automated Manufacturing");
		post3.setSlug("design-of-jigs-and-fixtures-in-automated-manufacturing");
		post3.setThumnailUrl("https://www.pre-scient.com/wp-content/uploads/2019/06/types-of-jigs-and-fixtures.webp");
		post3.setSummary(
				"Explains the principles of jig and fixture design and their importance in automated and robotic manufacturing.");
		post3.setContent("Jigs and fixtures are critical components in automated manufacturing systems.\r\n"
				+ "   Fixtures locate and hold workpieces accurately, while jigs additionally guide cutting tools.\r\n"
				+ "\r\n"
				+ "   In robotic and CNC-based production lines, fixtures must be designed to ensure repeatability,\r\n"
				+ "   positional accuracy, and compatibility with robotic grippers and sensors.\r\n"
				+ "   Proper application of GD&T, locating principles (3-2-1), and material selection\r\n"
				+ "   directly affects product quality and cycle time.\r\n" + "\r\n"
				+ "   In Body-in-White (BIW) automation, fixtures play a major role in welding accuracy and dimensional control.\r\n"
				+ "   Poor fixture design often leads to rework, scrap, and inconsistent assemblies.");
		post3.setPublished(true);

		Post post4 = new Post();
		post4.setTitle("Industrial Sensors Used in Automation Systems");
		post4.setSlug("industrial-sensors-used-in-automation-systems");
		post4.setThumnailUrl("https://www.conawayelectrical.com/wp-content/uploads/2023/11/automation-sensor.jpg");
		post4.setSummary(
				"A detailed look at industrial sensors used in automation, including proximity, photoelectric, and vision sensors.");
		post4.setContent("Industrial sensors enable machines and robots to perceive their environment.\r\n"
				+ "   Common sensors used in automation include inductive proximity sensors, capacitive sensors,\r\n"
				+ "   photoelectric sensors, pressure sensors, and vision cameras.\r\n" + "\r\n"
				+ "   These sensors are widely used for object detection, position feedback, quality inspection,\r\n"
				+ "   and safety monitoring.\r\n"
				+ "   Leading sensor manufacturers include IFM, SICK, Pepperl+Fuchs, Keyence, and Omron.\r\n" + "\r\n"
				+ "   In advanced automation systems, sensor data is processed by PLCs or industrial PCs\r\n"
				+ "   to make real-time control decisions, improving efficiency and reducing human intervention.");
		post4.setPublished(false);

		Post post5 = new Post();
		post5.setTitle("Mechanical Design Considerations for Robotic Automation");
		post5.setSlug("mechanical-design-considerations-for-robotic-automation");
		post5.setThumnailUrl("https://tistcochin.edu.in/wp-content/uploads/2024/06/BL-robotic-Evolution.jpg");
		post5.setSummary(
				"A comprehensive overview of Spring Boot architecture, explaining core components, auto-configuration, and application flow.");
		post5.setContent(
				"A draft article focusing on mechanical design aspects such as stiffness, payload, and accuracy in robotic automation.',\r\n"
						+ "  'Mechanical design plays a crucial role in the success of robotic automation systems.\r\n"
						+ "   Factors such as structural stiffness, payload capacity, vibration, and thermal expansion\r\n"
						+ "   directly impact robot accuracy and repeatability.\r\n" + "\r\n"
						+ "   Designers must consider load paths, joint clearances, material selection, and ease of maintenance\r\n"
						+ "   when developing automation equipment.\r\n"
						+ "   This draft article will later include case studies on robotic end-effectors,\r\n"
						+ "   fixture interaction, and real-world automation failures caused by poor mechanical design.");
		post5.setPublished(true);

		List<Post> posts = List.of(post1, post2, post3, post4, post5);
		postRepository.saveAll(posts);

		// Banners Data
		Banner banner1 = new Banner();
		// banner1.setId(1000);
		banner1.setTitle("Welcome Banner");
		banner1.setContent(
				"<div>\r\n" + "\r\n" + "    <div class=\"mb-3\">\r\n" + "<i class=\"bi bi-stars fs-3\"></i>\r\n"
						+ "    </div>\r\n" + "\r\n" + "    <h2 class=\"fw-bold mb-3\">\r\n"
						+ "        Welcome to 2025\r\n" + "    </h2>\r\n" + "\r\n" + "    <p class=\"mb-4\">\r\n"
						+ "        A new year of innovation, automation, and engineering excellence.\r\n"
						+ "        Let us build smarter systems and stronger designs together.\r\n" + "    </p>\r\n"
						+ "\r\n" + "    <div class=\"row justify-content-center mb-4\">\r\n" + "\r\n"
						+ "        <div class=\"col-6 col-md-3 mb-2\">\r\n"
						+ "            <i class=\"bi bi-robot d-block mb-1\"></i>\r\n"
						+ "            <small>Robotics</small>\r\n" + "        </div>\r\n" + "\r\n"
						+ "        <div class=\"col-6 col-md-3 mb-2\">\r\n"
						+ "            <i class=\"bi bi-gear-wide-connected d-block mb-1\"></i>\r\n"
						+ "            <small>Automation</small>\r\n" + "        </div>\r\n" + "\r\n"
						+ "        <div class=\"col-6 col-md-3 mb-2\">\r\n"
						+ "            <i class=\"bi bi-rulers d-block mb-1\"></i>\r\n"
						+ "            <small>Mechanical Design</small>\r\n" + "        </div>\r\n" + "\r\n"
						+ "        <div class=\"col-6 col-md-3 mb-2\">\r\n"
						+ "            <i class=\"bi bi-bar-chart-line d-block mb-1\"></i>\r\n"
						+ "            <small>Continuous Growth</small>\r\n" + "        </div>\r\n" + "\r\n"
						+ "    </div>\r\n" + "\r\n" + "    <p class=\"mb-3\">\r\n"
						+ "        — <strong>Hari</strong>\r\n" + "    </p>\r\n" + "\r\n" + "    <div>\r\n"
						+ "        <a href=\"/posts\" class=\"btn btn-outline-light btn-sm me-2\">\r\n"
						+ "            Read Articles\r\n" + "        </a>\r\n"
						+ "        <a href=\"/about\" class=\"btn btn-light btn-sm\">\r\n" + "            About Me\r\n"
						+ "        </a>\r\n" + "    </div>\r\n" + "\r\n" + "</div>\r\n" + "");

		Banner banner2 = new Banner();
		// banner2.setId(1001);
		banner2.setTitle("Spring Boot Learning Series");
		banner2.setContent("<section>\r\n" + "     <h2>Spring Boot Learning Series</h2>\r\n"
				+ "     <p>This series covers everything from basic concepts to advanced production-ready applications.</p>\r\n"
				+ "     <ul>\r\n" + "         <li>Spring Boot Basics</li>\r\n" + "         <li>REST APIs</li>\r\n"
				+ "         <li>Database Integration</li>\r\n" + "         <li>Security & Deployment</li>\r\n"
				+ "     </ul>\r\n" + " </section>");

		Banner banner3 = new Banner();
		// banner3.setId(1002);
		banner3.setTitle("Join Our Developer Community");
		banner3.setContent("<div>\r\n" + "     <h2>Join Our Developer Community</h2>\r\n"
				+ "     <p>Connect with backend developers, share knowledge, and grow together.</p>\r\n"
				+ "     <a href=\"/community\">Join Now</a>\r\n" + " </div>");

		List<Banner> banners = List.of(banner1, banner2, banner3);
		bannerRepository.saveAll(banners);

		// User Data
		LoginCredentials loginCredentials = new LoginCredentials();
		loginCredentials.setUsername("harikumarborra");
		loginCredentials.setPassword(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
		loginCredentials.setRole("SUPERADMIN");
		loginCredentials.setIsEnabled(true);
		loginCredentials.setProfile(profile);
		credentialsService.saveCredentials(loginCredentials);

		Subscriber subscriber = new Subscriber();
		subscriber.setName("Harikumar borra");
		subscriber.setEmail("harikumarborra3@gmail.com");
		subscriber.setActive(true);
		subscriberRepository.save(subscriber);
	}

}