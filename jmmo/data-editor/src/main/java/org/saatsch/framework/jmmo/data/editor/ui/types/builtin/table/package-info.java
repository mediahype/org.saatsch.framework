/**
 * contains builtin type editors for Collection<> types. Such Editors are displayed as tables.
 * 
 * when it has to create an editor for a collection, the framework chooses from the existing builtin
 * editors as follows:
 * 
 * <ul>
 * <li>{@code Collection<Pointer<?>>} -> {@link PointerListEditor}</li>
 * <li>{@code Collection<Enum>} -> {@link EnumListEditor}</li>
 * <li>everything else -> {@link TableEditor}</li>
 * </ul>
 * 
 * 
 */
package org.saatsch.framework.jmmo.data.editor.ui.types.builtin.table;
