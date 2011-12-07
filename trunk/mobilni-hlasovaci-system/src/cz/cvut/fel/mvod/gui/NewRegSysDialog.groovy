/*
Copyright 2011 Radovan Murin

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/
/*
 * © 2010, Jakub Valenta
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the Jakub Valenta
 * nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * This software is provided by the copyright holders and contributors “as is” and any
 * express or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are disclaimed.
 * In no event shall the foundation or contributors be liable for any direct, indirect,
 * incidental, special, exemplary, or consequential damages (including, but not limited to,
 * procurement of substitute goods or services; loss of use, data, or profits; or business
 * interruption) however caused and on any theory of liability, whether in contract, strict
 * liability, or tort (including negligence or otherwise) arising in any way out of the use
 * of this software, even if advised of the possibility of such damage.
 */

package cz.cvut.fel.mvod.gui
import java.awt.FlowLayout
import cz.cvut.fel.mvod.common.Voting
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.DefaultComboBoxModel
import groovy.swing.SwingBuilder
import javax.swing.JFrame
import javax.swing.WindowConstants
import cz.cvut.fel.mvod.common.EvaluationType
import cz.cvut.fel.mvod.persistence.DAOFacadeImpl
import cz.cvut.fel.mvod.global.GlobalSettingsAndNotifier
import cz.cvut.fel.mvod.persistence.DAOFactoryImpl
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action


/**
 * Dialog pro vytvoření nového hlasování.
 * @author jakub
 */
class NewRegSysDialog implements Showable {


        

	/**
	 * SwingBuilder pro vytvoření dialogu.
	 */
	def builder
        def regSysDAO = DAOFactoryImpl.instance.regSysDAO
	/**
	 * Nadřazené okno (JFrame).
	 */
	def owner

        /**
	 * JComboBox pro výběr akce.
	 */
	def eventComboBox
        def eventCheckBox
	/**
	 * JPanel zobrazující nastavení hlasování.
	 */
	def votingSettingsPanel
	/**
	 * JPanel zobrazující nastavení testu.
	 */
	def testSettingsPanel
	/**
	 * JComboBox pro výběr typu vyhodnocení testu.
	 */
	def testSettings
	/**
	 * JTextArea zobrazující nápovědu.
	 */
	def helpArea
        def final EVENTS = ["Sjezd strany (23.3.2009)", "Volba predsedy strany (12.2.2011)"]
	def final HELP = ["Za částečně správnou odpověď je přidělena část bodů.",
				"Body jsou přidělené pouze za správně zodpovězené otázky.",
				"Body se přičítají za správně zodpovězené otázky a odečítají za špatně zodpovězené."]
    
        


	NewRegSysDialog(SwingBuilder builder, JFrame owner) {
		this.builder = builder
		this.owner = owner
	}

	void show() {
	        
		newRegSysDialog.pack()
		newRegSysDialog.visible = true
	}

	void hide() {
		newRegSysDialog.visible = false
	}

	/**
	 * Vytvoří a uloží nové hlasování.
	 */
	def confirmImport = {
                System.out.println(eventComboBox.selectedIndex)
                if(eventCheckBox.selected) {
                    System.out.println("Ano");
                    regSysDAO.setImportEnabled(true)
                } else {
                    regSysDAO.setImportEnabled(false)
                    System.out.println("Ne");
                }
                regSysDAO.setAction(eventComboBox.selectedItem)
                //DAOFacadeImpl.instance.retrieveVotersFromRegSys(eventComboBox.selectedItem)
//		def voting = new Voting()
//		voting.test = votingType.selectedItem == TYPES[1]
//		if(!voting.test) {
//			voting.secret = votingSettings.selectedItem == VOTING_SETTINGS[1]
//			voting.minVoters = (Integer) votingMinParticipation.value
//		} else {
//			switch(testSettings.selectedItem) {
//				case TEST_SETTINGS[0]:
//					voting.evaluation = EvaluationType.PARTIAL_CORRECTNESS
//					break;
//				case TEST_SETTINGS[1]:
//					voting.evaluation = EvaluationType.ABSOLUTE_CORRECTNESS
//					break;
//				case TEST_SETTINGS[2]:
//					voting.evaluation = EvaluationType.ABSOLUTE_CORRECTNESS_NEGATIVE
//					break;
//			}
//		}
//		DAOFacadeImpl.instance.currentVoting = voting
		hide()
	}

	/**
	 * Dialog pro vytvoření nového hlasování.
	 */
	def newRegSysDialog = builder.dialog(
			title: "Import z registracniho systemu",
			maximumSize: [200, 200],
			resizable: false,
			owner: owner,
			layout: new BorderLayout(),
			modal: true,
			locationRelativeTo: owner,
			defaultCloseOperation: WindowConstants.DISPOSE_ON_CLOSE) {
		panel(constraints: BorderLayout.NORTH,
				layout: new FlowLayout()) {
			label(text: "Zvolte akci:")
			eventComboBox = comboBox(
                            model: new DefaultComboBoxModel(regSysDAO.getAkce().toArray()),
				selectedIndex: 0)
		}
                panel(constraints: BorderLayout.CENTER,
				layout: new FlowLayout()) {
			eventCheckBox = checkBox(selected: regSysDAO.isImportEnabled())
                            label(text: "Načítat účastníky z registračního systému")
		}

		panel(constraints: BorderLayout.SOUTH) {
			votingSettingsPanel = panel(layout: new GridLayout(2, 2)) {
				
			}
			testSettingsPanel = panel(
					visible: false,
					constraints: BorderLayout.CENTER,
					layout: new BorderLayout()) {
				panel(
						layout: new GridLayout(1, 2),
						constraints: BorderLayout.NORTH) {
					label(text: GlobalSettingsAndNotifier.singleton.messages.getString("evalTypeLabel"))
					
				}
				helpArea = textArea(
						editable: false,
						text: HELP[0],
						rows: 3,
						lineWrap: true,
						wrapStyleWord:true,
						constraints: BorderLayout.CENTER)
			}
		}
		panel(constraints: BorderLayout.SOUTH, layout: new FlowLayout(FlowLayout.RIGHT)) {
			button(text: "OK", actionPerformed: confirmImport)
			button(text: GlobalSettingsAndNotifier.singleton.messages.getString("cancelLabel"), actionPerformed: {hide()})
		}
	}

}

